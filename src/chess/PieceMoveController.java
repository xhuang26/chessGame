package chess;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import chess.Undo.MyNode;
import java.awt.Color;

	public class PieceMoveController implements Runnable{
	private ChessGUI view;
	private Game model;
	private Undo undos;
	public static final int WIDTH = 8;
	public static final int HEIGHT = 8;
	
	//game loop fields
	private Thread thread;
	private int FPS = 30;
	private long targetTime = 1000/FPS;
	private boolean isRunning = false;
		
	//states
	protected final static int NEED_NAMES = 0;
	protected final static int READY_START = 1;
	protected final static int TIE = 2;
	protected final static int ONE_WIN = 3;
	protected final static int READY_RESTART = 4; //still same pair of player
	protected final static int CHECKMATE = 5;
	protected final static int LULL = 6;
	protected final static int UNDO = 7;
			
	//state variables
	private int scoreWhite = 0;
	private int scoreBlack = 0;
	private String whiteName = "";
	private String blackName = "";
	protected boolean whiteWin = false;
	protected int state = PieceMoveController.NEED_NAMES;
	protected int requestTie = 0;
	

	
	/**
	 * initialize view, model, undo, add click listeners
	 */
	public PieceMoveController(){
		this.view = new ChessGUI(WIDTH, HEIGHT);
		this.model = new Game(HEIGHT,WIDTH);
		this.undos = new Undo();
		this.view.addPieceListeners(new PieceClickListener());
		this.addPlayerPanelListener((JButton)this.view.whitePanel[1], (JButton)this.view.whitePanel[2], (JButton)this.view.whitePanel[3], "white");
		this.addPlayerPanelListener((JButton)this.view.blackPanel[1], (JButton)this.view.blackPanel[2], (JButton)this.view.blackPanel[3], "black");
		this.addStartButtonListener((JButton)this.view.dialog[2], (JTextField)this.view.dialog[0], (JTextField)this.view.dialog[1]);
		this.addUndoButtonListener(this.view.undoBtn);
		this.view.frame.addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
				    stop();
				  }
				});
		start();

	}

	/**
	 * start game loop
	 */
	private void start(){
		isRunning = true;
		thread = new Thread(this, "Game loop");
		thread.start();
	}
	
	/**
	 * end game loop
	 */
	private void stop(){
		isRunning = false;
		try {
		thread.join();
		} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	/**
	* game loop
	*/
	@Override
	public void run() {
		long start, elapsed, wait;
		while(isRunning){
			start  = System.currentTimeMillis();
			update();
			elapsed = System.currentTimeMillis()-start;
			wait = targetTime - elapsed;
			if(wait <200) {wait = 200;}
			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	protected void reInitialize(){
		//clear stack
		this.undos.clear();
		
		//redraw game board
		this.view.frame.remove(this.view.boardGUI);
		this.view.boardGUI = new ChessBoardGUI(WIDTH, HEIGHT);
		this.view.frame.add(this.view.boardGUI);
		this.view.frame.revalidate();
		this.view.frame.repaint();
		
		//initialize model and add event listener
		this.model = new Game(HEIGHT,WIDTH);
		this.view.addPieceListeners(new PieceClickListener());
		
		//setback tie color
		this.view.whitePanel[2].setForeground(Color.black);
		this.view.blackPanel[2].setForeground(Color.black);
	}
	/**
	 * update the view and model based on the state
	 */
	private void update() {
		if(this.state == READY_START){ //when two new users are created
			//hide name input dialog
			JDialog dialog = (JDialog)(this.view.dialog[3]);
			dialog.setVisible(false);
			
			this.reInitialize();
			
			
			//set name and score for players
			JLabel whiteLabel = (JLabel)this.view.whitePanel[0];
			whiteLabel.setText(this.whiteName);
			JLabel blackLabel = (JLabel)this.view.blackPanel[0];
			blackLabel.setText(this.blackName);
			this.scoreWhite = 0;
			this.scoreBlack = 0;
			this.view.setScore(this.scoreWhite, this.view.whiteScoreLabel);
			this.view.setScore(this.scoreBlack, this.view.blackScoreLabel);
			this.requestTie = 0;
			this.state = LULL;
		} else if(this.state == NEED_NAMES){ //set user name 
			System.out.format("%s%n", "need names");
			JDialog dialog = (JDialog)(this.view.dialog[3]);
			dialog.setVisible(true);
			this.state = LULL;
		} else if(this.state == ONE_WIN){ //one side win, update score
			System.out.format("%s%n", "one win");
			if(this.whiteWin){
				this.scoreWhite += 100;
			} else {
				this.scoreBlack += 100;
			}			
			this.view.setScore(this.scoreWhite, this.view.whiteScoreLabel);
			this.view.setScore(this.scoreBlack, this.view.blackScoreLabel);
			this.state = READY_RESTART;
		} else if(this.state== READY_RESTART){ //still same user, but new run
			System.out.format("%s%n", "ready restart");
			this.reInitialize();
			this.state = LULL;
		} else if(this.state == TIE){ // increase score for both player
			System.out.format("%s%n", "tie");
			this.scoreBlack += 100;
			this.scoreWhite += 100;
			this.view.setScore(this.scoreWhite, this.view.whiteScoreLabel);
			this.view.setScore(this.scoreBlack, this.view.blackScoreLabel);
			this.state = READY_RESTART;
		} else if(this.state == CHECKMATE){ //checkmate, judge who's the winner
			if(this.model.nextPlayer instanceof PlayerWhite){
				this.whiteWin = false;
			} else {
				this.whiteWin = true;
			}
			this.state = ONE_WIN;
		} else if(this.state == UNDO){ //undo if the undo stack is not empty
			if(this.undos.empty()){
				view.messagePanel("sorry, you can't undo now");
			} else {
				MyNode undoNode = this.undos.pop();
				this.model.nextPlayer = undoNode.getPlayer();
				this.model.resetPiece(undoNode.getStartPosition().getX(), undoNode.getStartPosition().getY(), undoNode.getEndPosition().getX(), undoNode.getEndPosition().getY(), undoNode.getStart(), undoNode.getEnd());
				undoNode.getStart().hasNotMove = undoNode.isHasNotMove();
				this.view.undoPiece(undoNode.getStartGUI(), undoNode.getEndGUI(), undoNode.getLastImg());
				
			}
			this.state = LULL;	
		}
		
	}
	

	
	/**
	 * initialize the controller
	 * @param args
	 */
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new PieceMoveController();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	/**
	 * add listener for start button
	 * @param startButton
	 * @param field1
	 * @param field2
	 */
	protected void addStartButtonListener(JButton startButton, final JTextField field1, final JTextField field2){
		startButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String text1 = field1.getText();
	
				String text2 = field2.getText();
	
				System.out.format("%s%n", text1);
	
				if(text1.length()!=0 && text2.length() !=0){
	
					whiteName = text1;
		
					blackName = text2;
					
					System.out.format("whiteName: %s%n", whiteName);
					state = READY_START;
				}
			}
		});
	}
	
	/**
	 * add listener for undo button
	 * @param undoBtn
	 */
	protected void addUndoButtonListener(JButton undoBtn){
		undoBtn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				state = UNDO;
			}
			
		});
	}
	
	/**
	 * add listener for player panel
	 * @param forfiet
	 * @param tie
	 * @param leave
	 * @param type
	 */
	protected void addPlayerPanelListener(JButton forfiet, final JButton tie, JButton leave, final String type){
		
		forfiet.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(type == "white"){
					whiteWin = false;
				} else {
					whiteWin = true;
				}
				state = ONE_WIN;
			}
		});
		
		tie.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == (JButton)view.whitePanel[2]){
					System.out.format("white tie%n");
				} else if(e.getSource() == (JButton)view.blackPanel[2]){
					System.out.format("black tie%n");
				}
				requestTie++;
				System.out.format("requestTie: %d%n", requestTie);
				tie.setForeground(Color.gray);
				if(requestTie >= 2){
					state = PieceMoveController.TIE;
					requestTie = 0;
				}
			}
		});
		leave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				state =PieceMoveController.NEED_NAMES;
			}
		});
	
	}
	
	/**
	 * listener for piece move
	 *
	 */
	private class PieceClickListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {			
			ChessPieceGUI curPiece = (ChessPieceGUI)e.getComponent();
			if(view.getStart() == null && curPiece.getImg() != null){ //set current piece as start
				view.setStart(curPiece);
			} else if(view.getStart() != null){//start is set, this click is for end
				view.setEnd(curPiece);
				
				//modify x,y from view to model
				Position startPos = view.getStart().getPosition().getYX(); 
				Position endPos = view.getEnd().getPosition().getYX();
				
				//set fields for Undo in the future
				Piece lastStart = model.getBoard().getTile(startPos.getX(), startPos.getY()).getPiece();
				Piece lastEnd = model.getBoard().getTile(endPos.getX(), endPos.getY()).getPiece();
				ChessPieceGUI lastStartGUI = view.getStart();
				ChessPieceGUI lastEndGUI = view.getEnd();
				ImageIcon lastImg = view.getEnd().getImg();
				boolean notMove = lastStart.hasNotMove;
				Player lastPlayer = model.nextPlayer;
				
				//try move piece in model
				int moveCode = model.makeMove(startPos, endPos);
				
				//check the feedback of current move from model
				if(moveCode == Game.WRONG_TURN){
					view.messagePanel("wrong turn");
					view.setStart(null);
				}else if(moveCode == Game.BREAK_PIECE_RULE){
					view.messagePanel("illegal move");
					view.setStart(null);
				}else if(moveCode == Game.CHECK_MATE){
					view.setStart(null);
					String type = (model.nextPlayer instanceof PlayerWhite)? "black" : "white";
					view.messagePanel("it's a checkmate, winner:"+type);
					state = ONE_WIN; //set state for ending game
				}else if(moveCode == Game.STALE_MATE){
					view.setStart(null);
					view.messagePanel("it's a stalemate");
					state = TIE; //set state for ending game
				}else {
					view.movePiece();
					view.setStart(null);
					undos.add(lastStart, lastEnd, startPos, endPos,lastPlayer, notMove,  lastStartGUI, lastEndGUI, lastImg);
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}


}

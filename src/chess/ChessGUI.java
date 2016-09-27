package chess;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;



public class ChessGUI{

	protected JFrame frame;
	protected ChessBoardGUI boardGUI;
	public final static int MARGIN = 100;
	public final static int CELL_SIZE = 50;
	private ChessPieceGUI start = null;
	private ChessPieceGUI end = null;
	
	protected Object[] dialog;
	protected JComponent[] whitePanel, blackPanel;
	protected JButton undoBtn;
	protected JLabel whiteScoreLabel, blackScoreLabel;
	protected JLabel message = new JLabel("");
	
	public static final String BISHOP_BLACK = "src/imgs/bishopBlack.png";
    public static final String ROOK_BLACK = "src/imgs/rookBlack.png";
    public static final String KING_BLACK = "src/imgs/kingBlack.png";
    public static final String QUEEN_BLACK = "src/imgs/queenBlack.png";
    public static final String PAWN_BLACK = "src/imgs/pawnBlack.png";
    public static final String KNIGHT_BLACK = "src/imgs/knightBlack.png";
    public static final String BISHOP_WHITE = "src/imgs/bishopWhite.png";
    public static final String ROOK_WHITE = "src/imgs/rookWhite.png";
    public static final String KING_WHITE = "src/imgs/kingWhite.png";
    public static final String QUEEN_WHITE = "src/imgs/queenWhite.png";
    public static final String PAWN_WHITE = "src/imgs/pawnWhite.png";
    public static final String KNIGHT_WHITE = "src/imgs/knightWhite.png";
    public static final String HOPPER_BLACK = "src/imgs/hopperBlack.png";
    public static final String HOPPER_WHITE = "src/imgs/hopperWhite.png";
    public static final String ALFIL_BLACK = "src/imgs/alfilBlack.png";
    public static final String ALFIL_WHITE = "src/imgs/alfilWhite.png";

	/**
	 * Create the application.
	 */
	public ChessGUI(int width, int height) {
		initialize(width, height);
	}

	/**
	 * get boardGUI
	 * @return
	 */
	protected ChessBoardGUI getBoardGUI(){
		return this.boardGUI;
	}
	
	/**
	 * pop up message box
	 * @param text
	 */
	protected void messagePanel(String text){
		JOptionPane.showMessageDialog(this.frame, text, "status", JOptionPane.INFORMATION_MESSAGE);

	}
	/**
	 * Initialize the contents of the frame.
	 */
	protected void initialize(int width, int height) {
		int frameWidth = MARGIN*4 + CELL_SIZE * width;
		int frameHeight = MARGIN*2 + CELL_SIZE * height;
		frame = new JFrame();
		frame.setSize(frameWidth , frameHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Chess Game");
		frame.setBackground(Color.LIGHT_GRAY);
		frame.setVisible(true);
		this.whiteScoreLabel = new JLabel("", JLabel.CENTER);
		this.blackScoreLabel = new JLabel("", JLabel.CENTER);
		this.whitePanel = playerPanel(width, height, 3,"white", 0, whiteScoreLabel);
		this.blackPanel = playerPanel(width, height, 1,"black", 0, blackScoreLabel);
		this.undoBtn = this.undo(width, 5);
		setAllLabels(width, height);
		this.dialog = inputNameDialog();
		this.boardGUI = new ChessBoardGUI(width, height);
		frame.add(this.boardGUI);
		
	}
	
	/**
	 * set score for label
	 * @param score
	 * @param label
	 */
	protected void setScore(int score, JLabel label){
		label.setText("score: "+score);
	}

	
	/**
	 * set one piece at specific position
	 * @param x
	 * @param y
	 * @param text
	 * @return
	 */
	private JLabel setLabel(int x, int y, String text){
		JLabel label = new JLabel(text);
		label.setLayout(null);
		label.setBounds(x, y, 50, 50);
		return label;
	}
	
	/**
	 * set all piece
	 * @param width
	 * @param height
	 */
	protected void setAllLabels(int width, int height){
		for(int i=0; i<height; i++){
			int yValue = i*CELL_SIZE+MARGIN;
			String text = Integer.toString(height-i);
			this.frame.add(setLabel(CELL_SIZE, yValue, text));
			this.frame.add(setLabel(this.frame.getWidth()-2*MARGIN-CELL_SIZE, yValue, text));
		}
		char character = 'a';    
		int curAscii = (int) character;
		for(int i=0; i<width; i++){
			int xValue =  i*CELL_SIZE+MARGIN+25;
			String text = Character.toString((char)curAscii);
			curAscii++;
			this.frame.add(setLabel(xValue, CELL_SIZE, text));
			this.frame.add(setLabel(xValue, this.frame.getHeight()-MARGIN, text));
		}
	}
	
	
	/**
	 * set player info panel
	 * @param width
	 * @param hegiht
	 * @param y
	 * @param type
	 * @param score
	 * @param scoreLabel
	 * @return return Label, forfeit, tie, leave
	 */
	protected JComponent[] playerPanel(int width, int hegiht, int y, final String type, int score, JLabel scoreLabel){

		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(4,1));

		panel.setSize(150, 180);

		panel.setBackground( Color.white );

		panel.setLocation(MARGIN*2 + CELL_SIZE * width,MARGIN*y);

		JLabel name = new JLabel(" ( "+type+" chess)", JLabel.CENTER);

		this.setScore(score, scoreLabel);

		JPanel panelButtons = new JPanel();

		panelButtons.setLayout(new GridLayout(1,2));

		JButton forfiet = new JButton("forfiet");
		forfiet.setBorder(BorderFactory.createLineBorder(Color.white));

		JButton tie = new JButton("tie");
		tie.setBorder(BorderFactory.createLineBorder(Color.white));

		panel.add(name);

		panel.add(scoreLabel);

		panelButtons.add(forfiet);

		panelButtons.add(tie);

		panel.add(panelButtons);

		JButton leave = new JButton("leave");
		leave.setBorder(BorderFactory.createLineBorder(Color.white));

		panel.add(leave);
		
		this.frame.add(panel);
		JComponent[] array =  {name, forfiet, tie, leave};
		return array;

		}



	
	public ChessPieceGUI getStart() {
		return start;
	}

	/**
	 * set start position, change the old start background color
	 * @param start
	 */
	public void setStart(ChessPieceGUI start) {
		if(this.start != null){
			Position pos = this.start.getPosition();
			Color curColor = Color.orange;
			if((pos.getX() %2==0 && pos.getY()%2 ==0) || (pos.getX() %2==1 && pos.getY()%2 ==1)){
				curColor = Color.white;
			}
			this.start.setBackground(curColor);
		}
		this.start = start;
		if(start != null){
			start.setOpaque(true);
			start.setBackground(Color.gray);
		}
	}

	public ChessPieceGUI getEnd() {
		return end;
	}

	public void setEnd(ChessPieceGUI end) {
		this.end = end;
	}
	
	/**
	 * set the input dialog box
	 * @return white player input field, black player input field, start button, dialog pane
	 */
	protected Object[] inputNameDialog(){

		final JTextField field1 = new JTextField();

		final JTextField field2 = new JTextField();

		JButton startButton = new JButton("start");

		JPanel panel = new JPanel(new GridLayout(0, 1));

		panel.add(new JLabel("white chess name:"));

		panel.add(field1);

		panel.add(new JLabel("black chess name:"));

		panel.add(field2);

		panel.add(startButton);

		JDialog pane = new JDialog(this.frame, "input player name");

		pane.getRootPane().setWindowDecorationStyle(JRootPane.QUESTION_DIALOG);

		pane.setBounds( 100, 100, 300, 200 );

		pane.setLocationRelativeTo(this.frame);

		pane.add(panel);

		pane.setUndecorated(true);

		pane.setVisible(true);

		Object[] array = {field1, field2, startButton, pane};
		return array;
	}
	
	/**
	 * create undo button
	 * @param width
	 * @param y
	 * @return
	 */
	protected JButton undo(int width, int y){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,1));
		panel.setLocation(MARGIN*2 + CELL_SIZE * width,MARGIN*y);
		panel.setSize(150, 50);
		JButton undoBtn = new JButton("undo");
		undoBtn.setBackground(Color.white);
		undoBtn.setBorder(BorderFactory.createLineBorder(Color.white));
		//undoBtn.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.white, Color.white));
		panel.add(undoBtn);
		this.frame.add(panel);
		/*JButton undoBtn = new JButton("undo");
		undoBtn.setLocation(MARGIN*2 + CELL_SIZE * width,MARGIN*y);
		
		undoBtn.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.white, Color.white));
		undoBtn.setBackground(Color.white);
		undoBtn.setSize(150, 50);
		this.frame.add(undoBtn);*/
		return undoBtn;

	}
	
	/**
	 * add listener for each piece
	 * @param listener
	 */
	public void addPieceListeners(MouseListener listener){
		System.out.format("addPieceListeners%n");
		
		ArrayList<ChessPieceGUI> pieces = this.boardGUI.getPieces();
		for(ChessPieceGUI piece : pieces){
			piece.addMouseListener(listener);
		}
	}
	
	/**
	 * move piece from start to end
	 */
	public void movePiece(){
		ImageIcon prevStart = start.getImg();
		start.setImg(null);
		end.setImg(prevStart);
		
	}
	
	/**
	 * reset piece from end to start
	 * @param start
	 * @param end
	 * @param lastEndImg
	 */
	protected void undoPiece(ChessPieceGUI start, ChessPieceGUI end, ImageIcon lastEndImg){
		if(start == null || end == null){
			return;
		}
		start.setImg(end.getImg());
		end.setImg(lastEndImg);
	}
	
	/**
	 * get current frame
	 * @return this.jframe
	 */
	public JFrame getFrame(){
		return this.frame;
	}
	
	
}

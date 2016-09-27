package chess;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import java.awt.Image;
import java.awt.Color;
import java.util.ArrayList;
public class ChessBoardGUI extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ChessPieceGUI> pieces = new ArrayList<>();
	private int width;
	private int height;
	/**
	 * Create the panel.
	 */
	public ChessBoardGUI(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		this.setLayout(null);
		initPieces();
	}
	
	public void paintComponent(Graphics g){
		int width = ChessGUI.CELL_SIZE*this.width;
		int height = ChessGUI.CELL_SIZE*this.height;
		g.setColor(Color.orange);
		g.fillRect(ChessGUI.MARGIN, ChessGUI.MARGIN, width, height);
		
		for(int i=ChessGUI.MARGIN; i<=width-2*ChessGUI.CELL_SIZE+ChessGUI.MARGIN; i+=2*ChessGUI.CELL_SIZE){
			for(int j=ChessGUI.MARGIN; j<=height-2*ChessGUI.CELL_SIZE+ChessGUI.MARGIN; j+=2*ChessGUI.CELL_SIZE){
				g.clearRect(i, j, ChessGUI.CELL_SIZE, ChessGUI.CELL_SIZE);
			}
		}
		for(int i = ChessGUI.MARGIN+ChessGUI.CELL_SIZE; i <= width-ChessGUI.CELL_SIZE+ChessGUI.MARGIN; i+=2*ChessGUI.CELL_SIZE){
			for(int j = ChessGUI.MARGIN+ChessGUI.CELL_SIZE; j <= height-ChessGUI.CELL_SIZE+ChessGUI.MARGIN; j+=2*ChessGUI.CELL_SIZE){
                g.clearRect(i, j, ChessGUI.CELL_SIZE, ChessGUI.CELL_SIZE);
            }
        }
	}
	
	protected void initPieces(){
		System.out.format("init pieces%n");
		drawPiece(4,0, Game.KING, new PlayerBlack());
		drawPiece(3,0, Game.QUEEN, new PlayerBlack());
		drawPiece(1,0, Game.KNIGHT, new PlayerBlack());
		drawPiece(6,0, Game.KNIGHT, new PlayerBlack());
		drawPiece(5,0, Game.BISHOP, new PlayerBlack());
		drawPiece(2,0, Game.BISHOP, new PlayerBlack());
		drawPiece(0,0, Game.ROOK, new PlayerBlack());
		drawPiece(7,0, Game.ROOK, new PlayerBlack());
		drawPiece(2,2, Game.HOPPER, new PlayerBlack());
		drawPiece(5,2, Game.ALFIL, new PlayerBlack());
		for(int i=0; i<8; i++){ // draw pawns
			drawPiece(i,1, Game.PAWN, new PlayerBlack());
        }
		drawPiece(4,7, Game.KING, new PlayerWhite());
		drawPiece(3,7, Game.QUEEN, new PlayerWhite());
		drawPiece(1,7, Game.KNIGHT, new PlayerWhite());
		drawPiece(6,7, Game.KNIGHT, new PlayerWhite());
		drawPiece(5,7, Game.BISHOP, new PlayerWhite());
		drawPiece(2,7, Game.BISHOP, new PlayerWhite());
		drawPiece(0,7, Game.ROOK, new PlayerWhite());
		drawPiece(7,7, Game.ROOK, new PlayerWhite());
		drawPiece(2,5, Game.HOPPER, new PlayerWhite());
		drawPiece(5,5, Game.ALFIL, new PlayerWhite());
		for(int i=0; i<8; i++){ // draw pawns
			drawPiece(i,6, Game.PAWN, new PlayerWhite());
        }
		for(int i=0; i<8; i++){
			for(int j=2; j<6; j++){
				drawPiece(i,j
						);
			}
		}
	}
	protected void drawPiece(int xCell, int yCell){
		ChessPieceGUI pieceGUI = new ChessPieceGUI(xCell, yCell, null, this);
		this.pieces.add(pieceGUI);
		JLabel label = pieceGUI.getLable();
		add(label);
		
	}
	protected void drawPiece(int xCell, int yCell, String type, Player player){
		if(player == null) {return;}
		
		ImageIcon imgIcon;
		
		if(player instanceof PlayerBlack){
			
			if(type == Game.BISHOP){
				imgIcon = new ImageIcon(ChessGUI.BISHOP_BLACK);
			} else if(type == Game.QUEEN){
				imgIcon = new ImageIcon(ChessGUI.QUEEN_BLACK);
			}else if(type == Game.KING){
				imgIcon = new ImageIcon(ChessGUI.KING_BLACK);
			} else if(type == Game.KNIGHT){
				imgIcon = new ImageIcon(ChessGUI.KNIGHT_BLACK);
			}else if(type == Game.PAWN){
				imgIcon = new ImageIcon(ChessGUI.PAWN_BLACK);
			}else if(type == Game.ROOK){
				imgIcon = new ImageIcon(ChessGUI.ROOK_BLACK);
			}else if(type == Game.HOPPER){
				imgIcon = new ImageIcon(ChessGUI.HOPPER_BLACK);
			}else if(type == Game.ALFIL){
				imgIcon = new ImageIcon(ChessGUI.ALFIL_BLACK);
			}else {
				System.out.format("invalid type of chess to draw\n");
				return;
			}
		} else {
			if(type == Game.BISHOP){
				imgIcon = new ImageIcon(ChessGUI.BISHOP_WHITE);
			} else if(type == Game.QUEEN){
				imgIcon = new ImageIcon(ChessGUI.QUEEN_WHITE);
			}else if(type == Game.KING){
				imgIcon = new ImageIcon(ChessGUI.KING_WHITE);
			} else if(type == Game.KNIGHT){
				imgIcon = new ImageIcon(ChessGUI.KNIGHT_WHITE);
			}else if(type == Game.PAWN){
				imgIcon = new ImageIcon(ChessGUI.PAWN_WHITE);
			}else if(type == Game.ROOK){
				imgIcon = new ImageIcon(ChessGUI.ROOK_WHITE);
			}else if(type == Game.HOPPER){
				imgIcon = new ImageIcon(ChessGUI.HOPPER_WHITE);
			}else if(type == Game.ALFIL){
				imgIcon = new ImageIcon(ChessGUI.ALFIL_WHITE);
			} else {
				System.out.format("invalid type of chess to draw\n");
				return;
			}
		}
		Image image = imgIcon.getImage(); // transform it 
		Image newimg = image.getScaledInstance(ChessGUI.CELL_SIZE, ChessGUI.CELL_SIZE,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		imgIcon = new ImageIcon(newimg); 
		ChessPieceGUI pieceGUI = new ChessPieceGUI(xCell, yCell, imgIcon, this);
		this.pieces.add(pieceGUI);
		JLabel label = pieceGUI.getLable();
		add(label);
		
		//imgIcon.paintIcon(this, g, xGUI, yGUI);
	}
	
	protected ArrayList<ChessPieceGUI> getPieces(){
		return this.pieces;
	}






}

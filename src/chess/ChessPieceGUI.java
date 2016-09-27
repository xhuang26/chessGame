package chess;


import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class ChessPieceGUI extends JLabel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Position pos = new Position();
	private ImageIcon img;
	//private JLabel label;
	public ChessPieceGUI(int x, int y, ImageIcon img, ChessBoardGUI boardGUI){
		super(img, JLabel.CENTER);
		pos.setPosition(x, y);
		
		this.img = img;
		this.setLocation(x, y);
		this.setSize(ChessGUI.CELL_SIZE, ChessGUI.CELL_SIZE);
	}
	public JLabel getLable(){
		return this;
	}

	public ImageIcon getImg() {
		return this.img;
	}
	public void setImg(ImageIcon img){
		this.setIcon(img);
		this.img = img;
	}
	
	public Position getPosition(){
		return this.pos;
	}
	
	
	@Override
	public void setLocation(int x, int y){
		int xGUI = ChessGUI.MARGIN+x*ChessGUI.CELL_SIZE;
		int yGUI = ChessGUI.MARGIN+y*ChessGUI.CELL_SIZE;
		super.setLocation(xGUI, yGUI);
	}
	
}

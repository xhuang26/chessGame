package chess;
import java.util.ArrayList;


public abstract class Player {

    
    //All the pieces that belong to the current user
    private ArrayList<Piece> pieces;
    
    private Piece king = null;
    
    /**
     * Initialize the player
     */
	protected Player(){
		super();
		ArrayList<Piece> new_pieces = new ArrayList<>();
		this.setPieces(new_pieces);
	}
	
	/**
	 * Initialize different start positions for different type of Player
	 * @param board
	 */
	protected abstract void setInitialPosition(Board board);
	
	/**
	 * different user will have different forward direction on the board
	 * @return
	 */
	protected abstract int getDirection();
	

	protected ArrayList<Piece> getPieces() {
		return pieces;
	}
	
	protected void setPieces(ArrayList<Piece> pieces) {
		this.pieces = pieces;
	}
	
	/**
	 * create different type of piece on board based on the type input
	 * add the new piece to the pieces arrayList
	 * @param type
	 * @param x
	 * @param y
	 * @param board
	 * @return
	 */
	protected Piece addPiece(String type, int x, int y, Board board){
		Piece piece = null;
		int[] position = {x, y};
		if(type == Game.BISHOP){
			piece = new Bishop(this, position);
		} else if(type == Game.QUEEN){
			piece = new Queen(this, position);
		}else if(type == Game.KING){
			piece = new King(this, position);
		} else if(type == Game.KNIGHT){
			piece = new Knight(this, position);
		}else if(type == Game.PAWN){
			piece = new Pawn(this, position);
		}else if(type == Game.ROOK){
			piece = new Rook(this, position);
		} else if(type == Game.ALFIL){
			piece = new Alfil(this, position);
		} else if(type == Game.HOPPER){
			piece = new Hopper(this, position);
		} else {
			return null;
		}
		Tile tile = board.getTile(x, y);
		tile.setPiece(piece);
		this.pieces.add(piece);
		return piece;
	}
	
	protected abstract String getName();
	
	protected Piece getKing() {
		return king;
	}
	
	protected void setKing(Piece king) {
		this.king = king;
	}
	
}

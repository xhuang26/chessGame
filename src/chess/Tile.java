package chess;


public class Tile {
	private Piece piece;
	private boolean isEmpty = true;

	protected Tile(){
		super();
		this.setPiece(null);

		
	}

	protected Piece getPiece() {
		return piece;
	}
	
	/**
	 * besides set the piece, set the isEmpty value
	 * @param piece
	 */
	protected void setPiece(Piece piece) {
		if(piece == null){
			setEmpty(true);
		}else{
			setEmpty(false);
		}
		this.piece = piece;
	}
	protected boolean isEmpty() {
		return isEmpty;
	}
	protected void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}
	
}

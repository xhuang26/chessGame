package chess;

public class PlayerWhite extends Player{
	private final String name = "white";
	private final int direction = -1;
	
	/**
	 * initialize PlayerWhite
	 */
	protected PlayerWhite(){
		super();
	}
	
	@Override
	/**
	 * white chess should be put at top
	 */
	protected void setInitialPosition(Board board) {
		for(int i=0; i<8; i++){ // draw pawns
			super.addPiece(Game.PAWN, 6, i, board);
        }
		// draw rooks
		super.addPiece(Game.ROOK, 7, 7, board);
		super.addPiece(Game.ROOK,7, 0, board);
		// draw bishops
		super.addPiece(Game.BISHOP, 7, 2,  board);
		super.addPiece(Game.BISHOP, 7, 5,  board);
		//draw king
		super.addPiece(Game.KING, 7, 4, board);
		super.setKing(board.getTile(7, 4).getPiece());
        //draw queen
		super.addPiece(Game.QUEEN, 7, 3, board);
        //draw knight
		super.addPiece(Game.KNIGHT, 7, 1, board);
		super.addPiece(Game.KNIGHT, 7, 6, board);
		super.addPiece(Game.HOPPER, 5, 2, board);
		super.addPiece(Game.ALFIL, 5, 5, board);
	}
	
	@Override
	protected String getName() {
		return name;
	}

	protected int getDirection() {
		return direction;
	}
}

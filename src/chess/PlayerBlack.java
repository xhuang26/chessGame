package chess;

public class PlayerBlack extends Player {
	private final String name = "black";
	
	private final int direction = 1;
	
	/**
	 * initialize PlayerBlack
	 */
	protected PlayerBlack(){
		super();
	}
	
	@Override
	/**
	 * white chess should be put at bottom
	 */
	protected void setInitialPosition(Board board) {
		for(int i=0; i<8; i++){ // draw pawns
			super.addPiece(Game.PAWN, 1, i, board);
        }
		// draw rooks
		super.addPiece(Game.ROOK, 0, 7, board);
		super.addPiece(Game.ROOK,0, 0, board);
		// draw bishops
		super.addPiece(Game.BISHOP, 0, 2,  board);
		super.addPiece(Game.BISHOP, 0, 5,  board);
		//draw king
		super.addPiece(Game.KING, 0, 4, board);
		super.setKing(board.getTile(0, 4).getPiece());
        //draw queen
		super.addPiece(Game.QUEEN, 0, 3, board);
        //draw knight
		super.addPiece(Game.KNIGHT, 0, 1, board);
		super.addPiece(Game.KNIGHT, 0, 6, board);
		super.addPiece(Game.HOPPER, 2, 2, board);
		super.addPiece(Game.ALFIL, 2, 5, board);
	}
	
	@Override
	protected String getName() {
		return name;
	}

	protected int getDirection() {
		return direction;
	}

}

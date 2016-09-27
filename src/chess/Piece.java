package chess;
import java.util.ArrayList;


public abstract class Piece {
	private Player owner;
	//if the piece is eaten
	private boolean eaten;
	//current position on board
	private Position position;
	protected boolean hasNotMove = true;
	
	/**
	 * initialize the piece, original it is not eaten
	 */
	protected Piece(){
		this.setOwner(null);
		this.position = new Position();
		this.eaten = false;
	}
	
	/**
	 * Initialize the piece with owner and position info
	 * @param owner
	 * @param position
	 */
	protected Piece(Player owner, int[] position){
		this.setOwner(owner);
		this.position = new Position();
		this.position.setPosition(position);
	}
	

	protected Player getOwner() {
		return owner;
	}
	protected void setOwner(Player owner) {
		this.owner = owner;
	}
	
	/**
	 * check the type of the chess in sub class
	 * @return
	 */
	protected abstract String checkType();
	
	/**
	 * basic checking for the move on bard
	 * if it is out of boundary of the board
	 * @param end_x
	 * @param end_y
	 * @param board
	 * @return
	 */
	protected boolean isValid(int end_x, int end_y, Board board){
		int start_x = this.getPosition()[0];
		int start_y = this.getPosition()[1];
		if(start_x == end_x && start_y == end_y){
			return false;
		}
		if((0 <= start_x && start_x < board.getWidth()) && (0<=start_y && start_y< board.getWidth()) && (0<=end_x&& end_x< board.getHeight()) && (0<=end_y && end_y< board.getHeight())){
			return true;
		} else {
			return false;
		}
	}

	/**
	 * check if the current position is taken by opposite
	 * if it is taken by opposite or there is not piece on the tile, return true
	 * if it is taken by same player, return false
	 * @param end_x
	 * @param end_y
	 * @param board
	 * @return
	 */
	protected boolean ifTakenByOpposite(int end_x, int end_y, Board board){
		int start_x = this.getPosition()[0];
		int start_y = this.getPosition()[1];
		Piece start_piece = board.getTile(start_x, start_y).getPiece();
		Piece end_piece = board.getTile(end_x, end_y).getPiece();
		if(end_piece != null){
			if(start_piece.getOwner() != end_piece.getOwner()){
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
	/**
	 * check all the available move for the piece
	 * three aspects:
	 * 1. only position on the moving area for the type of the piece
	 * 2. within boundary
	 * 3. not occupied by the same color of chess
	 * @param board
	 * @return
	 */
	protected abstract ArrayList<int[]> availableMove(Board board);
	
	protected boolean isEaten() {
		return eaten;
	}
	protected void setEaten(boolean eaten) {
		this.eaten = eaten;
	}
	protected int[] getPosition() {
		return this.position.getPosition();
	}
	protected void setPosition(int[] position) {
		this.position.setPosition(position);
	}

}

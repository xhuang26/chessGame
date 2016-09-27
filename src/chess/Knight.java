package chess;
import java.util.ArrayList;


public class Knight extends Piece{
	protected Knight(){
		super();
	}
	protected Knight(Player owner, int[] position){
		super(owner, position);
	}
	@Override
	protected String checkType() {
		return Game.KNIGHT;
	}
	
	@Override
	/**
	 * The knight moves to any of the closest squares that are not on the same rank, file, or diagonal, thus the move forms an "L"-shape: two squares vertically and one square horizontally, or two squares horizontally and one square vertically. The knight is the only piece that can leap over other pieces.
	 */
	protected boolean isValid(int end_x, int end_y, Board board) {
		int start_x = this.getPosition()[0];
		int start_y = this.getPosition()[1];
		if(!super.isValid(end_x, end_y, board)){
			return false;
		}
		if(!super.ifTakenByOpposite(end_x, end_y, board)){
			return false;
		}
		
		int diff_x = Math.abs(start_x-end_x);
		int diff_y = Math.abs(start_y-end_y);
		if((diff_x == 2 && diff_y == 1) || (diff_x==1 && diff_y == 2)){
			return true;
		}
		return false;
	}
	@Override
	protected ArrayList<int[]> availableMove(Board board) {
		int start_x = this.getPosition()[0];
		int start_y = this.getPosition()[1];
		int[] direction1 = {1,-1};
		int[] direction2 = {2,-2};
		ArrayList<int[]> moves = new ArrayList<>();
		availableMoveHelper(moves, direction1, direction2, start_x, start_y, board);
		availableMoveHelper(moves, direction2, direction1, start_x, start_y, board);
		return moves;
	}
	
	/**
	 * add all available move to moves
	 * checking all the combinations for x moves in any direction of direction_x and y moves in any direction of direction_x
	 * @param moves
	 * @param direction_x
	 * @param direction_y
	 * @param start_x
	 * @param start_y
	 * @param board
	 */
	private void availableMoveHelper(ArrayList<int[]> moves, int[] direction_x, int[] direction_y, int start_x, int start_y, Board board){
		for(int x : direction_x){
			for(int y : direction_y){
				int check_x = start_x+x;
				int check_y = start_y+y;
				if(this.isValid(check_x, check_y, board)){
					int[] cur_pos = {check_x, check_y};
					moves.add(cur_pos);
				}
			}
		}
		return;
	}

}

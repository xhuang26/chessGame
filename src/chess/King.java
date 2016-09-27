package chess;
import java.util.ArrayList;


public class King extends Piece {
	protected King(){
		super();
	}
	protected King(Player owner, int[] position){
		super(owner, position);
	}
	@Override
	protected String checkType() {
		return Game.KING;
	}

	@Override
	/**
	 * The king moves one square in any direction.
	 */
	protected boolean isValid(int end_x, int end_y, Board board) {
		int start_x = this.getPosition()[0];
		int start_y = this.getPosition()[1];
		if(!super.isValid( end_x, end_y, board)){
			return false;
		}
		if(!super.ifTakenByOpposite(end_x, end_y, board)){
			return false;
		}
		int diff_x = Math.abs(start_x-end_x);
		int diff_y = Math.abs(start_y-end_y);
		if(diff_x>1 || diff_y>1 || (diff_x != 1 && diff_y != 1)){
			return false;
		}
		return true;
	}
	
	@Override
	protected ArrayList<int[]> availableMove(Board board) {
		int start_x = this.getPosition()[0];
		int start_y = this.getPosition()[1];
		ArrayList<int[]> moves = new ArrayList<>();
		int[] direction = {-1, 0, 1};
		for(int i : direction){
			for(int j : direction){
				if(this.isValid(start_x+i, start_y+j, board) ){
					int[] cur_pos = {start_x+i, start_y+j};
					moves.add(cur_pos);
				}
			}
		}
		return moves;
	}

}

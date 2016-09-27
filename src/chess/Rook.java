package chess;
import java.util.ArrayList;


public class Rook extends Piece{
	protected Rook(){
		super();
	}
	protected Rook(Player owner, int[] position){
		super(owner, position);
	}
	@Override
	protected String checkType() {
		return Game.ROOK;
	}
	@Override
	/**
	 * The rook can move any number of squares along any rank or file, but may not leap over other pieces.
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
		if(diff_x == 0){ //moving vertically
			int check_x = start_x;
			int unit_y = start_y<end_y? 1:-1;
			int check_y = start_y+unit_y;
			while(check_y != end_y){
				if(board.occupied(check_x, check_y)){
					return false;
				}
				check_y = check_y+unit_y;
			}
			return true;
		} else if(diff_y == 0){ //moving horizontally
			int check_y = start_y;
			int unit_x = start_x<end_x? 1:-1;
			int check_x = start_x+unit_x;
			while(check_x != end_x){
				if(board.occupied(check_x, check_y)){
					return false;
				}
				check_x = check_x+unit_x;
			}
			return true;
		}
		return false;
	}
	
	@Override
	protected ArrayList<int[]> availableMove(Board board) {
		int start_x = this.getPosition()[0];
		int start_y = this.getPosition()[1];
		int[] direction = {1, -1};
		ArrayList<int[]> moves = new ArrayList<>();
		for(int x : direction){
			int check_x = start_x+x;
			while(this.isValid(check_x, start_y, board)){
				int[] cur_pos = {check_x, start_y};
				moves.add(cur_pos);
				check_x = check_x + x;
			}
		}
		for(int y : direction){
			int check_y = start_y+y;
			while(this.isValid(start_x, check_y, board)){
				int[] cur_pos = {start_x, check_y};
				moves.add(cur_pos);
				check_y = check_y + y;
			}
		}
		return moves;
	}
	
}

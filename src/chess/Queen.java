package chess;
import java.util.ArrayList;


public class Queen extends Piece{
	protected Queen(){
		super();
	}
	/**
	 * initialize the queen
	 * @param owner
	 * @param position
	 */
	protected Queen(Player owner, int[] position){
		super(owner, position);
	}
	
	@Override
	/**
	 * type for queen is quee
	 */
	protected String checkType() {
		return Game.QUEEN;
	}
	
	@Override
	/**
	 * The queen combines the power of the rook and bishop and can move any number of squares along rank, file, or diagonal, but it may not leap over other pieces.
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
		int unit_x = start_x<end_x? 1:-1;
		int unit_y = start_y<end_y? 1:-1;
		if(Math.abs(start_x-end_x) == Math.abs(start_y-end_y)){
			int check_x = start_x+unit_x;
			int check_y = start_y+unit_y;
			while(check_x != end_x){
				if(board.occupied(check_x, check_y)){
					return false;
				}
				check_x = check_x + unit_x;
				check_y = check_y + unit_y;
			}
			return true;
		}
		int diff_x = Math.abs(start_x-end_x);
		int diff_y = Math.abs(start_y-end_y);
		if(diff_x == 0){
			int check_x = start_x;
			int check_y = start_y+unit_y;
			while(check_y != end_y){
				if(board.occupied(check_x, check_y)){
					return false;
				}
				check_y = check_y+unit_y;
			}
			
			return true;
		} else if(diff_y == 0){
			int check_y = start_y;
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
		ArrayList<int[]> moves = new ArrayList<>();
		//same as bishop
		int[] direction = {-1, 1};
		for(int x : direction){
			for(int y : direction){
				int check_x = start_x+x;
				int check_y = start_y+y;
				while(this.isValid(check_x, check_y, board)){
					int[] cur_pos = {check_x, check_y};
					System.out.format("%d,%d%n", check_x, check_y);
					moves.add(cur_pos);
					check_x = check_x + x;
					check_y = check_y + y;
				}
			}
		}
		//same as rock
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

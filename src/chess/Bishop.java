package chess;
import java.lang.Math;
import java.util.ArrayList;
public class Bishop extends Piece{
	protected Bishop(){
		super();
	}
	protected Bishop(Player owner, int[] position){
		super(owner, position);
	}
	
	@Override
	protected String checkType() {
		return Game.BISHOP;
	}
	
	@Override
	/**
	 * The bishop can move any number of squares diagonally, but may not leap over other pieces.
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
		return false;
	}
	
	@Override
	protected ArrayList<int[]> availableMove(Board board) {
		int start_x = this.getPosition()[0];
		int start_y = this.getPosition()[1];
		System.out.format("%d,%d%n", start_x, start_y);
		int[] direction = {-1, 1};
		ArrayList<int[]> moves = new ArrayList<>();
		for(int x : direction){
			for(int y : direction){
				int check_x = start_x+x;
				int check_y = start_y+y;
				while(this.isValid(check_x, check_y, board)){
					System.out.format("%d,%d%n", check_x, check_y);
					int[] cur_pos = {check_x, check_y};
					moves.add(cur_pos);
					check_x = check_x + x;
					check_y = check_y + y;
				}
			}
		}
		return moves;
	}
		
}
package chess;
import java.util.ArrayList;


public class Pawn extends Piece{
	//checking if the current pawn has not moved since the game started
	protected Pawn(){
		super();
	}
	protected Pawn(Player owner, int[] position){
		super(owner, position);
	}
	@Override
	protected String checkType() {
		return Game.PAWN;
	}
	@Override
	/**
	 * The pawn may move forward to the unoccupied square immediately in front of it on the same file; or on its first move it may advance two squares along the same file provided both squares are unoccupied; or it may move to a square occupied by an opponent's piece which is diagonally in front of it on an adjacent file, capturing that piece.
	 */
	protected boolean isValid(int end_x, int end_y, Board board) {
		
		int start_x = this.getPosition()[0];
		int start_y = this.getPosition()[1];
		if(!super.isValid(end_x, end_y, board)){
			return false;
		}
		int diff_y = Math.abs(start_y-end_y);
		int diff_x = end_x - start_x;
		int valid_diff_x = board.getTile(start_x, start_y).getPiece().getOwner().getDirection();
		int valid_diff_x2 = valid_diff_x * 2;
		//System.out.format("valid_diff_x2: %d; diff_x: %d%n", valid_diff_x2, diff_x);
		//System.out.format("diff_y %d%n", diff_y);
		
		//moving forward
		if(diff_y == 0){
			
			if(!super.ifTakenByOpposite(end_x, end_y, board)){
				return false;
			}
			if(board.occupied(end_x, end_y)){
				return false;
			}
			if(diff_x == valid_diff_x){							//moving one step
				if(super.hasNotMove){
					super.hasNotMove = false;
				} 
				return true;
			} else if(this.hasNotMove && diff_x == valid_diff_x2){ //moving two steps for the first move
				super.hasNotMove = false;
				
				return true;
			}
		} else if(diff_x == valid_diff_x && diff_y == 1){ //eating pieces at diagonal
			if(board.occupied(end_x, end_y) && super.ifTakenByOpposite(end_x, end_y, board)){
				if(super.hasNotMove){super.hasNotMove = false;}
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	protected ArrayList<int[]> availableMove(Board board) {
		int start_x = this.getPosition()[0];
		int start_y = this.getPosition()[1];
		ArrayList<int[]> moves = new ArrayList<>();
		boolean needReset = false;
		
		//since only checking all the availableMove without change any status for the Pawn piece, after isValid method set hasNotMove, it need to be reset
		if(super.hasNotMove == true){
			needReset = true;
		}
		int valid_diff_x = board.getTile(start_x, start_y).getPiece().getOwner().getDirection();
		//moving forward
		for(int i=1; i<=2; i++){
			if(this.isValid(start_x+(i*valid_diff_x), start_y, board)){
				int[] cur_pos = {start_x+(i*valid_diff_x), start_y};
				moves.add(cur_pos);
				if(needReset) {super.hasNotMove = true;}
			}
		}
		//moving diagonal with eating opposite
		int[] direction = {-1,1};
		for(int i: direction){
			if(this.isValid(start_x+valid_diff_x, start_y+i, board)){
				int[] cur_pos = {start_x+valid_diff_x, start_y+i};
				moves.add(cur_pos);
				if(needReset) {super.hasNotMove = true;}
			}
		}
		return moves;
	}

}

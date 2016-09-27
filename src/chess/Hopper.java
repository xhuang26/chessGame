package chess;

import java.util.ArrayList;

public class Hopper extends Piece{
	protected Hopper(){
		super();
	}
	/**
	 * initialize the Hopper
	 * @param owner
	 * @param position
	 */
	protected Hopper(Player owner, int[] position){
		super(owner, position);
	}
	
	@Override
	/**
	 * type for queen is Game.HOPPER
	 */
	protected String checkType() {
		return Game.HOPPER;
	}
	
	@Override
	/**
	 * The hopper can only move vertically and it needs to jump over another piece in order to move. After it jumped over one piece, it will be landed on the next tile in front of the last piece it jumped over. The Hopper cannot jump over more than one piece. If the next tile is taken by the opposite, hopper will eat the opposite
	 */
	protected boolean isValid(int end_x, int end_y, Board board) {
		//System.out.format("%s%n", "try move queen");
		int start_x = this.getPosition()[0];
		int start_y = this.getPosition()[1];
		if(!super.isValid(end_x, end_y, board)){
			return false;
		}
		if(!super.ifTakenByOpposite(end_x, end_y, board)){
			return false;
		}
		int unit_x = start_x<end_x? 1:-1;
		int jumped_index = -1;
		if(end_y-start_y == 0){
			int check_x = start_x+unit_x;
			boolean findPiece = false;
			while(check_x != end_x){
				if(board.occupied(check_x, start_y)){
					if(!findPiece){
						findPiece = true;
						jumped_index = check_x;
						break;
					} else {
						return false;
					}
				}
				check_x = check_x+unit_x;
			}
			if(findPiece && jumped_index+unit_x == end_x){
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
		
		int[] direction = {-1 ,1};
		for(int x : direction){
			int check_x = start_x + x;
			while(super.isValid(check_x, start_y, board)){
				if(isValid(check_x, start_y, board)){
					int[] cur_pos = {check_x, start_y};
					moves.add(cur_pos);	
				}
				check_x = check_x + x;
			}
		}
		return moves;
	}
}

package chess;

import java.util.ArrayList;


public class Alfil extends Piece{
	/**
	 * initalize alfil without arguments
	 */
	protected Alfil(){
		super();
	}
	/**
	 * initialize the alfil
	 * @param owner
	 * @param position
	 */
	protected Alfil(Player owner, int[] position){
		super(owner, position);
	}
	
	@Override
	/**
	 * type for Alfil is Game.ALFIL
	 */
	protected String checkType() {
		return Game.ALFIL;
	}
	
	@Override
	/**
	 * Alfil jumps two squares diagonally, leaping over intermediate pieces.
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
		
		int diff_x = Math.abs(start_x-end_x);
		int diff_y = Math.abs(start_y-end_y);
		if(diff_x == 2 && diff_y == 2){
			return true;
		}
		return false;
	}
	
	@Override
	/**
	 * return all moves that available for Alfil
	 */
	protected ArrayList<int[]> availableMove(Board board) {
		int start_x = this.getPosition()[0];
		int start_y = this.getPosition()[1];
		ArrayList<int[]> moves = new ArrayList<>();
		
		int[] direction = {-2 ,2};
		for(int x : direction){
			for(int y : direction){
				int check_x = start_x+x;
				int check_y = start_y+y;
				if(this.isValid(check_x, check_y, board)){
					int[] cur_pos = {check_x, check_y};
					moves.add(cur_pos);
				}
			}
		}
		return moves;
	}
}

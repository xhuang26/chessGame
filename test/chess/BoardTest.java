package chess;
import static org.junit.Assert.*;

import org.junit.Test;


public class BoardTest {

	/**
	 * test initializing board with valid dimension
	 * @throws RuntimeException
	 */
	@Test
	public void initializeBoard() throws RuntimeException {
		int height = 7;
		int width = 8;
		Board board = new Board(width, height);
		assertEquals(height, board.getHeight());
		assertEquals(width, board.getWidth());
	}
	
	/**
	 * test initalizing board with invalid dimention
	 * should throw exception
	 * @throws OutOfValidRange
	 */
	 @Test(expected=OutOfValidRange.class)
	 public void negativeWidth() throws OutOfValidRange {
		int height = 7;
		int width = -4;
		new Board(width, height);
	 }
	 
	 /**
	  * check if Board.occupied function can detect when a tile is empty
	  * @throws OutOfValidRange
	  */
	 @Test
	 public void EmptySpotCheck() throws OutOfValidRange{
		 int height = 8;
		 int width = 8;
		 Board board = new Board(width, height);
		 assertFalse(board.occupied(3,4));
	 }
	 
	 /**
	  * test when input wrong index in the occupied() function
	  * @throws OutOfValidRange
	  */
	 @Test(expected=OutOfValidRange.class)
	 public void InvalidIndexInputForOccupied() throws OutOfValidRange{
		 int height = 8;
		 int width = 8;
		 Board board = new Board(width, height);
		 assertFalse(board.occupied(-1,4));
	 }

}


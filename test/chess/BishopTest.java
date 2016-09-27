package chess;
import static org.junit.Assert.*;
import chess.Board;
import chess.PlayerWhite;
import chess.PlayerBlack;
import chess.Piece;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;
public class BishopTest {
	private static PlayerWhite player_black;
	private static PlayerBlack player_white;
	private static Board board;
	private static final int width = 8;
	private static final int height =8;
	
	@BeforeClass
	public static void setUpBeforeClass(){
		player_black = new PlayerWhite();
		player_white = new PlayerBlack();	
	}
	@Before
	public void setUpBeforeEach(){
		board = new Board(width, height);
		player_white.setInitialPosition(board);
		player_black.setInitialPosition(board);
	}
	
	/**
	 * test the constructor for bishop
	 */
	@Test
	public void InitializePawnWithouArgs() {
		Bishop bishop = new Bishop();
		assertEquals(bishop.isEaten(), false);
		assertNull(bishop.getOwner());
		assertEquals(bishop.getPosition()[0], -1);
		assertEquals(bishop.getPosition()[1], -1);
	}
	
	/**
	 * test the constructor with owner and position setted
	 */
	@Test
	public void InitializePawnWithArgs() {
		int[] pos = {1, 1};
		Bishop bishop = new Bishop(player_black, pos);
		assertEquals(player_black, bishop.getOwner());
		assertEquals(pos[0], bishop.getPosition()[0]);
		assertEquals(pos[1], bishop.getPosition()[1]);
	}
	
	/**
	 * test if bishop can jump over other pieces
	 */
	@Test
	public void noJumpOver(){
		Piece bishop = board.getTile(7, 2).getPiece();
		assertEquals(bishop.checkType(), "bish");
		assertFalse(bishop.isValid(6, 3, board));
		assertFalse(bishop.isValid(6, 4, board));
		assertFalse(bishop.isValid(5, 3, board));
		assertEquals(bishop.availableMove(board).size(), 0);
	}
	
	/**
	 * test all the possible moves can be done when bishop is at (4,3)
	 */
	@Test
	public void someMovesAvailable(){
		int[] pos = {7, 2};
		Piece bishop = board.getTile(pos[0], pos[1]).getPiece();
		int[] newPos = {4,3};
		board.getTile(newPos[0], newPos[1]).setPiece(bishop);
		board.getTile(pos[0], pos[1]).setPiece(null);
		bishop.setPosition(newPos);
		assertTrue(bishop.isValid(newPos[0]+1, newPos[1]+1, board));
		assertFalse(bishop.isValid(newPos[0]+2, newPos[1]+2, board));
		assertFalse(bishop.isValid(newPos[0]+3, newPos[1]+3, board));
		assertTrue(bishop.isValid(newPos[0]-1, newPos[1]-1, board));
		assertTrue(bishop.isValid(newPos[0]-2, newPos[1]-2, board));
		assertTrue(bishop.isValid(newPos[0]-3, newPos[1]-3, board));
		assertTrue(bishop.isValid(newPos[0]+1, newPos[1]-1, board));
		assertFalse(bishop.isValid(newPos[0]+2, newPos[1]-2, board));
		assertFalse(bishop.isValid(newPos[0]+3, newPos[1]-3, board));
		assertTrue(bishop.isValid(newPos[0]-1, newPos[1]+1, board));
		assertTrue(bishop.isValid(newPos[0]-2, newPos[1]+2, board));
		assertTrue(bishop.isValid(newPos[0]-3, newPos[1]+3, board));
		assertFalse(bishop.isValid(newPos[0]-4, newPos[1]+4, board));
		assertEquals(bishop.availableMove(board).size(),8);
	}
	

}


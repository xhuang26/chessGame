package chess;
import static org.junit.Assert.*;
import chess.Board;
import chess.PlayerWhite;
import chess.PlayerBlack;
import chess.Piece;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;
public class QueenTest {
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
	 * test initializing queen without arguments
	 */
	@Test
	public void InitializePawnWithouArgs() {
		Queen queen = new Queen();
		assertEquals(queen.isEaten(), false);
		assertNull(queen.getOwner());
		assertEquals(queen.getPosition()[0], -1);
		assertEquals(queen.getPosition()[1], -1);
	}
	
	/**
	 * test pawn constructor with owner and position set
	 */
	@Test
	public void InitializePawnWithArgs() {
		int[] pos = {1, 1};
		Queen queen = new Queen(player_black, pos);
		assertEquals(player_black, queen.getOwner());
		assertEquals(pos[0], queen.getPosition()[0]);
		assertEquals(pos[1], queen.getPosition()[1]);
	}
	
	/**
	 * queen should not have jump overs
	 */
	@Test
	public void noJumpOver(){
		Piece queen = board.getTile(0, 3).getPiece();
		assertEquals(queen.checkType(), "quee");
		assertFalse(queen.isValid(0, 4, board));
		assertFalse(queen.isValid(1, 2, board));
		assertFalse(queen.isValid(0, 3, board));
		assertEquals(queen.availableMove(board).size(), 0);
	}
	
	/**
	 * test available moves for queen when it is at (4,3)
	 */
	@Test
	public void someMovesAvailable(){
		
		int[] pos = {0, 3};
		Piece queen = board.getTile(pos[0], pos[1]).getPiece();
		int[] newPos = {4,3};
		board.getTile(newPos[0], newPos[1]).setPiece(queen);
		board.getTile(pos[0], pos[1]).setPiece(null);
		queen.setPosition(newPos);
		System.out.format("%s%n", board.getTile(newPos[0], newPos[1]).getPiece().checkType());
		System.out.format("%b%n", queen.isValid(newPos[0]+2, newPos[1]+2, board));
		assertTrue(queen.isValid(newPos[0]+1, newPos[1]+1, board));
		
		assertTrue(queen.isValid(newPos[0]+2, newPos[1]+2, board));
		assertFalse(queen.isValid(newPos[0]+3, newPos[1]+3, board));
		assertTrue(queen.isValid(newPos[0]-1, newPos[1]-1, board));
		assertTrue(queen.isValid(newPos[0]-2, newPos[1]-2, board));
		assertFalse(queen.isValid(newPos[0]-3, newPos[1]-3, board));
		assertTrue(queen.isValid(newPos[0]+1, newPos[1]-1, board));
		assertTrue(queen.isValid(newPos[0]+2, newPos[1]-2, board));
		assertFalse(queen.isValid(newPos[0]+3, newPos[1]-3, board));
		assertTrue(queen.isValid(newPos[0]-1, newPos[1]+1, board));
		assertTrue(queen.isValid(newPos[0]-2, newPos[1]+2, board));
		assertFalse(queen.isValid(newPos[0]-3, newPos[1]+3, board));
		assertFalse(queen.isValid(newPos[0]-4, newPos[1]+4, board));
		
		assertTrue(queen.isValid(newPos[0]+1, newPos[1], board));
		assertTrue(queen.isValid(newPos[0]+2, newPos[1], board));
		assertFalse(queen.isValid(newPos[0]+3, newPos[1], board));
		assertTrue(queen.isValid(newPos[0]-1, newPos[1], board));
		assertTrue(queen.isValid(newPos[0]-2, newPos[1], board));
		assertFalse(queen.isValid(newPos[0]-3, newPos[1], board));
		assertFalse(queen.isValid(newPos[0]-4, newPos[1], board));
		assertTrue(queen.isValid(newPos[0], newPos[1]-1, board));
		assertTrue(queen.isValid(newPos[0], newPos[1]-2, board));
		assertTrue(queen.isValid(newPos[0], newPos[1]-3, board));
		assertTrue(queen.isValid(newPos[0], newPos[1]+1, board));
		assertTrue(queen.isValid(newPos[0], newPos[1]+2, board));
		assertTrue(queen.isValid(newPos[0], newPos[1]+3, board));
		assertTrue(queen.isValid(newPos[0], newPos[1]+4, board));
		assertEquals(queen.availableMove(board).size(),19);
	}
	

}


package chess;
import static org.junit.Assert.*;
import chess.Board;
import chess.PlayerWhite;
import chess.PlayerBlack;
import chess.Piece;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;
public class KingTest {
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
	 * test pawn constructor without argument
	 */
	@Test
	public void InitializePawnWithouArgs() {
		King piece = new King();
		assertEquals(piece.isEaten(), false);
		assertNull(piece.getOwner());
		assertEquals(piece.getPosition()[0], -1);
		assertEquals(piece.getPosition()[1], -1);
	}
	
	/**
	 * test pawn constructor with position and owner
	 */
	@Test
	public void InitializePawnWithArgs() {
		int[] pos = {1, 1};
		King king = new King(player_black, pos);
		assertEquals(player_black, king.getOwner());
		assertEquals(pos[0], king.getPosition()[0]);
		assertEquals(pos[1], king.getPosition()[1]);
	}
	
	/**
	 * test available moves for king when it is at (2,4)
	 */
	@Test
	public void someMovesAvailable(){
		int[] pos = {0, 4};
		Piece king = board.getTile(pos[0], pos[1]).getPiece();
		int[] newPos = {2,4};
		board.getTile(newPos[0], newPos[1]).setPiece(king);
		board.getTile(pos[0], pos[1]).setPiece(null);
		king.setPosition(newPos);
		System.out.format("%d,%d,%s%n", king.getPosition()[0],king.getPosition()[1],  board.getTile(newPos[0], newPos[1]).getPiece().checkType());
		assertTrue(king.isValid(newPos[0]+1, newPos[1], board));
		assertTrue(king.isValid(newPos[0]+1, newPos[1]-1, board));
		assertTrue(king.isValid(newPos[0]+1, newPos[1]+1, board));
		assertTrue(king.isValid(newPos[0], newPos[1]-1, board));
		assertTrue(king.isValid(newPos[0], newPos[1]+1, board));
		assertFalse(king.isValid(newPos[0]+2, newPos[1], board));
		assertFalse(king.isValid(newPos[0]-1, newPos[1], board));
		assertEquals(king.availableMove(board).size(), 5);
	}
	
	/**
	 * test when the beginning position is set for both player and king can't move
	 */
	@Test
	public void noMovesAvailable(){
		Piece king = board.getTile(0, 4).getPiece();
		assertFalse(king.isValid(0, 3, board));
		assertFalse(king.isValid(0, 5, board));
		assertFalse(king.isValid(1, 3, board));
		assertFalse(king.isValid(1, 5, board));
		assertFalse(king.isValid(2, 3, board));	
		assertEquals(king.availableMove(board).size(), 0);
	}

}

package chess;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AlfilTest {
	private static Board board;
	private static final int width = 8;
	private static final int height =8;
	
	@Before
	public void setUpBeforeEach(){
		board = new Board(width, height);
	}
	/**
	 * test the constructor for Alfil
	 */
	@Test
	public void InitializeAlfilWithouArgs() {
		Alfil alfil = new Alfil();
		assertEquals(alfil.isEaten(), false);
		assertNull(alfil.getOwner());
		assertEquals(alfil.getPosition()[0], -1);
		assertEquals(alfil.getPosition()[1], -1);
	}
	/**
	 * test the constructor with owner and position setted
	 */
	@Test
	public void InitializeAlfilWithArgs() {
		int[] pos = {1, 1};
		Player player_black = new PlayerBlack();
		Alfil alfil = new Alfil(player_black, pos);
		assertEquals(player_black, alfil.getOwner());
		assertEquals(pos[0], alfil.getPosition()[0]);
		assertEquals(pos[1], alfil.getPosition()[1]);
	}
	/**
	 * test if alfil can jump over other pieces
	 */
	@Test
	public void canJumpOver(){
		int[] pos = {2, 1};
		int[] whitePawnPos = {3,2};
		int[] blackPawnPos = {1,2};
		Player player_black = new PlayerBlack();
		Player player_white = new PlayerWhite();
		Alfil alfil = new Alfil(player_black, pos);
		board.getTile(pos[0], pos[1]).setPiece(alfil);
		
		Pawn pawnWhite = new Pawn(player_white, whitePawnPos);
		Pawn pawnBlack = new Pawn(player_black, blackPawnPos);
		board.getTile(whitePawnPos[0], whitePawnPos[1]).setPiece(pawnWhite);
		board.getTile(blackPawnPos[0], blackPawnPos[1]).setPiece(pawnBlack);
		assertTrue(alfil.isValid(4, 3, board));
		assertTrue(alfil.isValid(0, 3, board));
	}
	/**
	 * test available moves for alfil when it is at (4,4)
	 */
	@Test
	public void someMovesAvailable(){
		Player player_black = new PlayerBlack();
		Player player_white = new PlayerWhite();
		int[] alfilPos = {4,4};
		int[] queenPos = {6, 6};
		int[] pawnPos = {2,6};
		Queen queen = new Queen(player_black, queenPos);
		Pawn pawn = new Pawn(player_white, pawnPos);
		board.getTile(queenPos[0], queenPos[1]).setPiece(queen);
		board.getTile(pawnPos[0], pawnPos[1]).setPiece(pawn);
		Alfil alfil = new Alfil(player_white, alfilPos);
		board.getTile(alfilPos[0], alfilPos[1]).setPiece(alfil);
		assertTrue(alfil.isValid(6, 6, board));
		assertFalse(alfil.isValid(2, 6, board));
		assertTrue(alfil.isValid(2, 2, board));
		assertTrue(alfil.isValid(6, 2, board));
		assertEquals(alfil.availableMove(board).size(), 3);
	}
	
	
}

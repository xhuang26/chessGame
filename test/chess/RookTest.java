package chess;
import static org.junit.Assert.*;
import chess.Board;
import chess.PlayerWhite;
import chess.PlayerBlack;
import chess.Piece;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;
public class RookTest {
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
	 * test rook constructor and check if the owner is null, eaten is false and position is -1
	 */
	@Test
	public void InitializePawnWithouArgs() {
		Knight piece = new Knight();
		assertEquals(piece.isEaten(), false);
		assertNull(piece.getOwner());
		assertEquals(piece.getPosition()[0], -1);
		assertEquals(piece.getPosition()[1], -1);
	}
	
	/**
	 * test constructor with owner and position setted
	 */
	@Test
	public void InitializePawnWithArgs() {
		int[] pos = {1, 1};
		Rook rook = new Rook(player_black, pos);
		assertEquals(player_black, rook.getOwner());
		assertEquals(pos[0], rook.getPosition()[0]);
		assertEquals(pos[1], rook.getPosition()[1]);
	}
	
	/**
	 * should have no jump over other pieces
	 */
	@Test
	public void noJumpOver(){
		Piece rook = board.getTile(0, 0).getPiece();
		assertEquals(rook.checkType(), "rook");
		assertFalse(rook.isValid(0, 1, board));
		assertFalse(rook.isValid(0, 2, board));
		assertFalse(rook.isValid(1, 0, board));
		assertFalse(rook.isValid(1, 1, board));
		assertEquals(rook.availableMove(board).size(), 0);
	}
	
	/**
	 * test available moves for rook when it is at (5,3) at the board
	 */
	@Test
	public void someMovesAvailable(){
		int[] pos = {0, 0};
		Piece rook = board.getTile(pos[0], pos[1]).getPiece();
		int[] newPos = {5,3};
		board.getTile(newPos[0], newPos[1]).setPiece(rook);
		board.getTile(pos[0], pos[1]).setPiece(null);
		rook.setPosition(newPos);
		//System.out.format("%d,%d,%s%n", knight.getPosition()[0], knight.getPosition()[1],  board.getTile(newPos[0], newPos[1]).getPiece().checkType());
		assertTrue(rook.isValid(newPos[0]+1, newPos[1], board));
		assertFalse(rook.isValid(newPos[0]+2, newPos[1], board));
		assertTrue(rook.isValid(newPos[0]-1, newPos[1], board));
		assertTrue(rook.isValid(newPos[0]-2, newPos[1], board));
		assertTrue(rook.isValid(newPos[0]-3, newPos[1], board));
		assertFalse(rook.isValid(newPos[0]-4, newPos[1], board));
		assertFalse(rook.isValid(newPos[0]-5, newPos[1], board));
		assertTrue(rook.isValid(newPos[0], newPos[1]-1, board));
		assertTrue(rook.isValid(newPos[0], newPos[1]-2, board));
		assertTrue(rook.isValid(newPos[0], newPos[1]-3, board));
		assertTrue(rook.isValid(newPos[0], newPos[1]+1, board));
		assertTrue(rook.isValid(newPos[0], newPos[1]+2, board));
		assertTrue(rook.isValid(newPos[0], newPos[1]+3, board));
		assertTrue(rook.isValid(newPos[0], newPos[1]+4, board));
		assertEquals(rook.availableMove(board).size(), 11);
	}
	

}


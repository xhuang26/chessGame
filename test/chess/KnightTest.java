package chess;
import static org.junit.Assert.*;
import chess.Board;
import chess.PlayerWhite;
import chess.PlayerBlack;
import chess.Piece;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;
public class KnightTest {
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
	 * test knight constructor without arguments
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
	 * initalize pawn with onwer and position
	 */
	@Test
	public void InitializePawnWithArgs() {
		int[] pos = {1, 1};
		Knight knight = new Knight(player_black, pos);
		assertEquals(player_black, knight.getOwner());
		assertEquals(pos[0], knight.getPosition()[0]);
		assertEquals(pos[1], knight.getPosition()[1]);
	}
	
	/**
	 * no jump over other chess for knight
	 */
	@Test
	public void jumpOver(){
		Piece knight = board.getTile(0, 1).getPiece();
		assertEquals(knight.checkType(), "knig");
		assertFalse(knight.isValid(3, 2, board));
		assertFalse(knight.isValid(0, 2, board));
		assertEquals(knight.availableMove(board).size(), 2);
	}
	
	/**
	 * test available moves for knight when it is at (5, 4)
	 */
	@Test
	public void allMovesAvailable(){
		int[] pos = {0, 1};
		Piece knight = board.getTile(pos[0], pos[1]).getPiece();
		int[] newPos = {5,4};
		board.getTile(newPos[0], newPos[1]).setPiece(knight);
		board.getTile(pos[0], pos[1]).setPiece(null);
		knight.setPosition(newPos);
		assertTrue(knight.isValid(newPos[0]+2, newPos[1]+1, board));
		assertTrue(knight.isValid(newPos[0]+1, newPos[1]+2, board));
		assertTrue(knight.isValid(newPos[0]-1, newPos[1]+2, board));
		assertTrue(knight.isValid(newPos[0]+2, newPos[1]-1, board));
		assertTrue(knight.isValid(newPos[0]-2, newPos[1]+1, board));
		assertTrue(knight.isValid(newPos[0]+1, newPos[1]-2, board));
		assertTrue(knight.isValid(newPos[0]-2, newPos[1]-1, board));
		assertTrue(knight.isValid(newPos[0]-1, newPos[1]-2, board));
		assertEquals(knight.availableMove(board).size(), 8);
	}
	

}


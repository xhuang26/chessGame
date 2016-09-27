package chess;
import static org.junit.Assert.*;

import java.util.ArrayList;

import chess.Board;
import chess.PlayerWhite;
import chess.PlayerBlack;
import chess.Piece;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;
public class PawnTest {
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
	 * test constructor without arguments
	 */
	@Test
	public void InitializePawnWithouArgs() {
		Pawn piece = new Pawn();
		assertEquals(piece.isEaten(), false);
		assertNull(piece.getOwner());
		assertEquals(piece.getPosition()[0], -1);
		assertEquals(piece.getPosition()[1], -1);
	}
	
	/**
	 * test constructor with owner and position set
	 */
	@Test
	public void InitializePawnWithArgs() {
		int[] pos = {1, 1};
		Pawn pawn = new Pawn(player_black, pos);
		assertEquals(player_black, pawn.getOwner());
		assertEquals(pos[0], pawn.getPosition()[0]);
		assertEquals(pos[1], pawn.getPosition()[1]);
	}
	
	/**
	 * test when black pawn is moving in incorrect direction
	 */
	@Test
	public void validBlackDirection(){
		Piece pawnBlack = board.getTile(6,0).getPiece();
		int[] pos = pawnBlack.getPosition();
		boolean moveOneStep_black = pawnBlack.isValid(pos[0]-1, pos[1], board);
		assertTrue(moveOneStep_black);		
	}
	
	/**
	 * test when white pawn is moving in incorrect direction
	 */
	@Test
	public void invalidBlackDirection(){
		Piece pawnBlack = board.getTile(6,0).getPiece();
		int[] pos = pawnBlack.getPosition();
		boolean moveOneStep_black = pawnBlack.isValid(pos[0]+1, pos[1], board);
		assertFalse(moveOneStep_black);		
	}
	
	/**
	 * test white pawn moving in right direction
	 */
	@Test
	public void validWhiteDirection(){
		Piece pawnWhite = board.getTile(1,0).getPiece();
		int[] pos = pawnWhite.getPosition();
		boolean moveOneStep_black = pawnWhite.isValid(pos[0]+1, pos[1], board);
		assertTrue(moveOneStep_black);	
	}
	
	/**
	 * test black pawn moving in right direction
	 */
	@Test
	public void invalidWhiteDirection(){
		Piece pawnWhite = board.getTile(1,0).getPiece();
		int[] pos = pawnWhite.getPosition();
		boolean moveOneStep_black = pawnWhite.isValid(pos[0]-1, pos[1], board);
		assertFalse(moveOneStep_black);	
	}
	
	/**
	 * test if pawn can move two steps forward for its first move
	 */
	@Test
	public void validStartTwoStepForward(){
		Piece pawnBlack = board.getTile(6,3).getPiece();
		int[] pos = pawnBlack.getPosition();
		boolean moveOneStep_black = pawnBlack.isValid(pos[0]-2, pos[1], board);
		assertTrue(moveOneStep_black);		
	}
	
	/**
	 * test if pawn can move one step forward
	 */
	@Test
	public void validOneStepForward(){
		Piece pawnWhite = board.getTile(1,0).getPiece();
		int[] pos = pawnWhite.getPosition();
		int[] new_pos = {pos[0]+1, pos[1]};
		boolean moveOneStep_black = pawnWhite.isValid(new_pos[0], new_pos[1], board);
		board.getTile(pos[0], pos[1]).setPiece(null);
		board.getTile(new_pos[0], new_pos[1]).setPiece(pawnWhite);
		pawnWhite.setPosition(new_pos);
		pos = pawnWhite.getPosition();
		int[] new_pos2 = {pos[0]+1, pos[1]};
		moveOneStep_black = pawnWhite.isValid(new_pos2[0], new_pos2[1], board);
		assertTrue(moveOneStep_black);	
	}
	
	/**
	 * test if pawn can move diagonally with eathing oppsite chess
	 */
	@Test 
	public void eatingMove(){
		Piece eatingTarget = board.getTile(6,0).getPiece();
		int[] new_pos = {2,0};
		board.getTile(6, 0).setPiece(null);
		board.getTile(2, 0).setPiece(eatingTarget);
		eatingTarget.setPosition(new_pos);

		Piece testTarget = board.getTile(1, 1).getPiece();
		assertTrue(testTarget.isValid(2,0, board));
	}
	
	/**
	 * test that pawn cannot move diagonally when no chess can be eat
	 */
	@Test 
	public void invalidMoveDiagonal(){
		Piece testTarget = board.getTile(1, 1).getPiece();
		assertFalse(testTarget.isValid(2,0, board));
		assertFalse(testTarget.isValid(2,2, board));
	}
	
	/**
	 * test when the pawn move more than one steps
	 */
	@Test
	public void moveMoreThanOneStep(){
		Piece testTarget = board.getTile(1, 1).getPiece();
		assertFalse(testTarget.isValid(2,5, board));
		assertFalse(testTarget.isValid(3,6, board));
	}
	
	/**
	 * test that pawn cannot move backward
	 */
	@Test
	public void moveBackward(){
		Piece testTarget = board.getTile(6,0).getPiece();
		assertTrue(testTarget.isValid(4,0, board));
		int[] new_pos = {4,0};
		board.getTile(6, 0).setPiece(null);
		board.getTile(4, 0).setPiece(testTarget);
		testTarget.setPosition(new_pos);
		assertFalse(testTarget.isValid(5,0, board));
	}
	
	/**
	 * test valid moves for pawn when it is at (6,0)
	 */
	@Test
	public void availableMoves(){
		int[] target_pos = {6,0};
		Piece target_piece = board.getTile(target_pos[0], target_pos[1]).getPiece();
		ArrayList<int[]> moves = target_piece.availableMove(board);
		assertEquals(moves.size(), 2);	
		assertEquals(moves.get(0)[1], target_pos[1]);
		assertEquals(moves.get(1)[1], target_pos[1]);
		if(moves.get(0)[0] == 5){
			assertEquals(moves.get(1)[0], 4);
		} else if(moves.get(0)[0] ==4){
			assertEquals(moves.get(1)[0], 5);
		}else{
			assertTrue(false);
		}
	}

}


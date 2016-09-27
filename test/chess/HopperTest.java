package chess;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class HopperTest {
	private static Board board;
	private static final int width = 8;
	private static final int height =8;
	
	@Before
	public void setUpBeforeEach(){
		board = new Board(width, height);
	}
	/**
	 * test the constructor for Hopper
	 */
	@Test
	public void InitializeAlfilWithouArgs() {
		Hopper hopper = new Hopper();
		assertEquals(hopper.isEaten(), false);
		assertNull(hopper.getOwner());
		assertEquals(hopper.getPosition()[0], -1);
		assertEquals(hopper.getPosition()[1], -1);
	}
	/**
	 * test the constructor with owner and position setted
	 */
	@Test
	public void InitializeAlfilWithArgs() {
		int[] pos = {1, 1};
		Player player_black = new PlayerBlack();
		Hopper hopper = new Hopper(player_black, pos);
		assertEquals(player_black, hopper.getOwner());
		assertEquals(pos[0], hopper.getPosition()[0]);
		assertEquals(pos[1], hopper.getPosition()[1]);
	}
	
	/**
	 * test if hopper can jump over other piece and land on the next one
	 */
	@Test
	public void canJumpOverOnePiece(){
		int[] pos = {2, 1};
		int[] whitePawnPos = {3,1};
		Player player_black = new PlayerBlack();
		Player player_white = new PlayerWhite();
		Hopper hopper = new Hopper(player_black, pos);
		board.getTile(pos[0], pos[1]).setPiece(hopper);
		
		Pawn pawnWhite = new Pawn(player_white, whitePawnPos);
		board.getTile(whitePawnPos[0], whitePawnPos[1]).setPiece(pawnWhite);
		assertTrue(hopper.isValid(4, 1, board));
		assertFalse(hopper.isValid(5, 1, board));
		assertEquals(hopper.availableMove(board).size(), 1);
	}
	
	/**
	 * hopper can go backward
	 */
	@Test
	public void canGoBackward(){
		int[] pos = {3, 1};
		int[] whitePawnPos = {2,1};
		Player player_black = new PlayerBlack();
		Player player_white = new PlayerWhite();
		Hopper hopper = new Hopper(player_black, pos);
		board.getTile(pos[0], pos[1]).setPiece(hopper);
		
		Pawn pawnWhite = new Pawn(player_white, whitePawnPos);
		board.getTile(whitePawnPos[0], whitePawnPos[1]).setPiece(pawnWhite);
		assertTrue(hopper.isValid(1, 1, board));
		assertFalse(hopper.isValid(0, 1, board));
		assertEquals(hopper.availableMove(board).size(), 1);
	}
	
	/**
	 * if the landing position is taken by opposite, it's fine, but if the piece from same side is taken, then the move is illegal
	 */
	@Test
	public void eatPiece(){
		int[] pos = {3, 1};
		int[] whitePawnPos = {1,1};
		int[] blackPawnPos = {5,1};
		int[] jumpedPiecePos1 = {2,1};
		int[] jumpedPiecePos2 = {4,1};
		Player player_black = new PlayerBlack();
		Player player_white = new PlayerWhite();
		Hopper hopper = new Hopper(player_black, pos);
		board.getTile(pos[0], pos[1]).setPiece(hopper);
		Hopper jumpedPiece1 = new Hopper(player_black, jumpedPiecePos1);
		board.getTile(jumpedPiecePos1[0], jumpedPiecePos1[1]).setPiece(jumpedPiece1);
		Hopper jumpedPiece2 = new Hopper(player_black, jumpedPiecePos2);
		board.getTile(jumpedPiecePos2[0], jumpedPiecePos2[1]).setPiece(jumpedPiece2);
		
		Pawn pawnWhite = new Pawn(player_white, whitePawnPos);
		board.getTile(whitePawnPos[0], whitePawnPos[1]).setPiece(pawnWhite);
		Pawn pawnBlack = new Pawn(player_black, blackPawnPos);
		board.getTile(blackPawnPos[0], blackPawnPos[1]).setPiece(pawnBlack);
		assertTrue(hopper.isValid(1, 1, board));
		assertFalse(hopper.isValid(5, 1, board));
		assertEquals(hopper.availableMove(board).size(), 1);
	}
	
	/**
	 * cannot move if there is no piece to jump
	 */
	@Test
	public void mustJump(){
		int[] pos = {3, 1};
		Player player_black = new PlayerBlack();
		Hopper hopper = new Hopper(player_black, pos);
		board.getTile(pos[0], pos[1]).setPiece(hopper);
		assertFalse(hopper.isValid(4, 1, board));
		assertFalse(hopper.isValid(2, 1, board));
		assertEquals(hopper.availableMove(board).size(), 0);
	}
	
	/**
	 * can not jump multiple pieces
	 */
	@Test
	public void cannotJumpMoreThanOne(){
		int[] pos = {2, 1};
		int[] pawnPos1 = {4,1};
		int[] pawnPos2 = {6,1};
		Player player_black = new PlayerBlack();
		Player player_white = new PlayerWhite();
		Hopper hopper = new Hopper(player_black, pos);
		board.getTile(pos[0], pos[1]).setPiece(hopper);
		
		Pawn pawn1 = new Pawn(player_white, pawnPos1);
		board.getTile(pawnPos1[0], pawnPos1[1]).setPiece(pawn1);
		Pawn pawn2 = new Pawn(player_white, pawnPos2);
		board.getTile(pawnPos2[0], pawnPos2[1]).setPiece(pawn2);
		assertTrue(hopper.isValid(5, 1, board));
		assertFalse(hopper.isValid(6, 1, board));
		assertFalse(hopper.isValid(7, 1, board));
		assertEquals(hopper.availableMove(board).size(), 1);
	}

}

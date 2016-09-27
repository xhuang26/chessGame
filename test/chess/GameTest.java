package chess;
import java.util.Map;


import static org.junit.Assert.*;

import org.junit.Test;

public class GameTest {
	private static final int width = 8;
	private static final int height =8;

	/**
	 * test the game initializer with width and height
	 */
	@Test
	public void gameInitialzier(){
		Game game = new Game(width, height);
		assertNotNull(game.getBoard());
		assertEquals(game.getBoard().getWidth(), width);
		assertEquals(game.getBoard().getHeight(), height);
	}
	
	/**
	 * check if players are correctly initialized when the initPlayers() function is called
	 */
	@Test 
	public void shouldInitPlayers(){
		Game game = new Game(width, height);
		Map<String, Player> players = game.initPlayers();
		assertEquals(players.get("white").getName(), "white");
		assertEquals(players.get("black").getName(), "black");
	}
	
	/**
	 * try set a piece at empty tile and check if it is actually moved on board
	 */
	@Test
	public void  setPieceAtEmptySpot(){
		Game game = new Game(width, height);
		game.initPlayers();
		int[] pos = {1,0}; //pawn
		int[] new_pos = {2,0}; //empty spot
		Piece pawn = game.getBoard().getTile(pos[0], pos[1]).getPiece();
		assertTrue(game.getBoard().getTile(new_pos[0], new_pos[1]).isEmpty());
		game.setPiece(new_pos[0], new_pos[1], pawn);
		assertFalse(game.getBoard().getTile(new_pos[0], new_pos[1]).isEmpty());
		assertTrue(game.getBoard().getTile(pos[0], pos[1]).isEmpty());
		assertEquals(game.getBoard().getTile(new_pos[0], new_pos[1]).getPiece(), pawn);
	}
	@Test
	public void replacePiece(){
		Game game = new Game(width, height);
		game.initPlayers();
		int[] posPawn = {1,0}; //pawn
		Piece pawn = game.getBoard().getTile(posPawn[0], posPawn[1]).getPiece();
		posPawn = pawn.getPosition();
		game.setPiece(posPawn[0]+1, posPawn[1], pawn);//2,0
		int[] posRook = {0,0};
		int[] orig_posRook = posRook;
		int[] target_pos = {6,1};
		Piece rook = game.getBoard().getTile(posRook[0], posRook[1]).getPiece();
		Piece pawnEaten = game.getBoard().getTile(target_pos[0], target_pos[1]).getPiece();
		posRook = rook.getPosition();//0,0
		game.setPiece(posRook[0]+1, posRook[1], rook);//1,0
		posRook = rook.getPosition();
		game.setPiece(posRook[0], posRook[1]+1, rook);//1,1
		posRook = rook.getPosition();
		
		assertFalse(game.getBoard().getTile(posRook[0], posRook[1]).isEmpty());
		assertEquals(pawnEaten.checkType(), "pawn");
		game.setPiece(target_pos[0], target_pos[1], rook);//6,1
		posRook = rook.getPosition();
		assertTrue(game.getBoard().getTile(orig_posRook[0], orig_posRook[1]).isEmpty());
		assertFalse(game.getBoard().getTile(posRook[0], posRook[1]).isEmpty());
		assertEquals(game.getBoard().getTile(posRook[0], posRook[1]).getPiece(), rook);
		assertTrue(pawnEaten.isEaten());
	}
	
	@Test
	public void prevPieceNullResetPiece(){
		Game game = new Game(width, height);
		game.initPlayers();
		int[] pos = {1,0}; //pawn
		int[] new_pos = {2,0}; //empty spot
		Piece pawn = game.getBoard().getTile(pos[0], pos[1]).getPiece();
		game.setPiece(new_pos[0], new_pos[1], pawn);
		game.resetPiece(pos[0], pos[1], new_pos[0], new_pos[1], pawn, null);
		assertEquals(game.getBoard().getTile(pos[0], pos[1]).getPiece(), pawn);
		assertTrue(game.getBoard().getTile(new_pos[0], new_pos[1]).isEmpty());
	}
	
	/**
	 * check if setting a piece at a tile that is occupied by other chess is working correctly on board
	 */
	@Test
	public void prevPieceNotNullresetPiece(){
		Game game = new Game(width, height);
		game.initPlayers();
		int[] posPawn = {1,0}; //pawn
		Piece pawn = game.getBoard().getTile(posPawn[0], posPawn[1]).getPiece();
		posPawn = pawn.getPosition();
		game.setPiece(posPawn[0]+1, posPawn[1], pawn);//2,0
		int[] posRook = {0,0};
		int[] orig_posRook = posRook;
		int[] target_pos = {6,1};
		Piece rook = game.getBoard().getTile(posRook[0], posRook[1]).getPiece();
		Piece pawnEaten = game.getBoard().getTile(target_pos[0], target_pos[1]).getPiece();
		posRook = rook.getPosition();//0,0
		game.setPiece(posRook[0]+1, posRook[1], rook);//1,0
		posRook = rook.getPosition();
		game.setPiece(posRook[0], posRook[1]+1, rook);//1,1
		posRook = rook.getPosition();
		game.setPiece(target_pos[0], target_pos[1], rook);
		game.resetPiece(orig_posRook[0], orig_posRook[1], target_pos[0], target_pos[1], rook, pawnEaten);
		assertEquals(game.getBoard().getTile(orig_posRook[0], orig_posRook[1]).getPiece(), rook);
		assertEquals(game.getBoard().getTile(target_pos[0], target_pos[1]).getPiece(), pawnEaten);
		assertFalse(pawnEaten.isEaten());
	}
	
	/**
	 * check if exposeKing can judge correctly when the kings is not exposed
	 */
	@Test 
	public void KingUnexposed(){
		Game game = new Game(width, height);
		Map<String, Player> players = game.initPlayers();
		assertFalse(game.exposeKing(players.get("white")));
		assertFalse(game.exposeKing(players.get("black")));
	}
	
	/**
	 * check if exposeKing can judge correctly when the kings is exposed
	 */
	@Test 
	public void exposeKing(){
		Game game = new Game(width, height);
		game.initPlayers();
		int[] posPawn = {1,0}; //pawn
		Piece pawn = game.getBoard().getTile(posPawn[0], posPawn[1]).getPiece();
		posPawn = pawn.getPosition();
		game.setPiece(posPawn[0]+1, posPawn[1], pawn);//2,0
		int[] posRook = {0,0};
		int[] target_pos = {6,4};
		Piece rook = game.getBoard().getTile(posRook[0], posRook[1]).getPiece();
		Piece pawnEaten = game.getBoard().getTile(target_pos[0], target_pos[1]).getPiece();
		posRook = rook.getPosition();//0,0
		game.setPiece(posRook[0]+1, posRook[1], rook);//1,0
		posRook = rook.getPosition();
		game.setPiece(posRook[0], posRook[1]+4, rook);//1,4
		posRook = rook.getPosition();
		
		assertFalse(game.getBoard().getTile(posRook[0], posRook[1]).isEmpty());
		assertEquals(pawnEaten.checkType(), "pawn");
		game.setPiece(target_pos[0], target_pos[1], rook);//6,4
		assertTrue(game.exposeKing(pawnEaten.getOwner()));
	}
	
	
	/**
	 * check if the makeMove function can return right code when it's wrong turn for the player
	 */
	@Test
	public void WrongTurnInMakeMove(){
		Game game = new Game(width, height);
		game.initPlayers();
		assertEquals(game.makeMove(new Position(6,0), new Position(5, 0)), Game.NOT_END);
		assertEquals(game.makeMove(new Position(5, 0) , new Position(4, 0)), Game.WRONG_TURN);
	}
	
	/**
	 * check if makeMove can detect a checkmate after a move
	 */
	@Test
	public void MakeCheckMateMove(){
		Game game = new Game(width, height);
		Board board = game.getBoard();
		PlayerWhite white = new PlayerWhite();
		PlayerBlack black = new PlayerBlack();
		game.setPlayerBlack(black);
		game.setPlayerWhite(white);

		Piece kingWhite = white.addPiece("king", 3, 4, board);
		Piece kingBlack = black.addPiece("king", 3, 7, board);
		white.addPiece("rook", 7, 7, board);
		game.setNextPlayer(white);
		white.setKing(kingWhite);
		black.setKing(kingBlack);
		assertEquals(game.makeMove(new Position(3, 4), new Position(3, 5)), Game.CHECK_MATE);
	}
	
	/**
	 * check if makeMove can detect a stalemate after make a move
	 */
	@Test
	public void makeStaleMateMove(){
		Game game = new Game(width, height);
		Board board = game.getBoard();
		PlayerWhite white = new PlayerWhite();
		PlayerBlack black = new PlayerBlack();
		game.setPlayerBlack(black);
		game.setPlayerWhite(white);
		
		Piece kingWhite = white.addPiece("king", 7, 0, board);
		Piece kingBlack = black.addPiece("king", 0, 0, board);
		white.addPiece("quee", 2, 2, board);
		game.setNextPlayer(white);
		white.setKing(kingWhite);
		black.setKing(kingBlack);
		assertEquals(game.makeMove(new Position(2, 2), new Position(1, 2)), Game.STALE_MATE);
	}
	
	
	
}

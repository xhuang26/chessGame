package chess;
import chess.Board;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

public class PlayerBlackTest {
	private static final int width = 8;
	private static final int height =8;
	private static Board board;
	
	@Before
	public void setUpBeforeEach(){
		board = new Board(width, height);
	}
	/**
	 * test the blackPlayer initializer and the name, pieces and king attributes inside
	 */
	@Test
	public void PlayerInitializer() {
		PlayerBlack player = new PlayerBlack();
		assertNull(player.getKing());
		assertEquals(player.getPieces().size(), 0);
		assertEquals(player.getName(), "black");
	}
	
	/**
	 * check if all the pieces for BlackPlayer is initialized correctly
	 */
	@Test
	public void shouldSetInitialPosition(){
		PlayerBlack player = new PlayerBlack();
		player.setInitialPosition(board);
		for(int i=0; i<8; i++){
			assertEquals(board.getTile(1, i).getPiece().checkType(), "pawn");
		}
		assertEquals(board.getTile(0,7).getPiece().checkType(), "rook");
		assertEquals(board.getTile(0,0).getPiece().checkType(), "rook");
		
		assertEquals(board.getTile(0,2).getPiece().checkType(), "bish");
		assertEquals(board.getTile(0,5).getPiece().checkType(), "bish");
		
		assertEquals(board.getTile(0,4).getPiece().checkType(), "king");
		
		assertEquals(board.getTile(0,3).getPiece().checkType(), "quee");
		
		assertEquals(board.getTile(0,1).getPiece().checkType(), "knig");
		assertEquals(board.getTile(0,6).getPiece().checkType(), "knig");
		
		for(int i=2; i<board.getWidth(); i++){
			for(int j=0; j<board.getHeight(); j++){
				assertEquals(board.getTile(i, j).isEmpty(), true);
			}
		}
		
	}

}

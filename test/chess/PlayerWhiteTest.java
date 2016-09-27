package chess;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

public class PlayerWhiteTest {
	private static final int width = 8;
	private static final int height =8;
	private static Board board;
	
	@Before
	public void setUpBeforeEach(){
		board = new Board(width, height);
	}
	
	/**
	 * test the white_layer initializer and the name, pieces and king attributes inside
	 */
	@Test
	public void PlayerInitializer() {
		PlayerWhite player = new PlayerWhite();
		assertNull(player.getKing());
		assertEquals(player.getPieces().size(), 0);
		assertEquals(player.getName(), "white");
	}
	
	/**
	 * check if all the pieces for WhitePlayer is initialized correctly
	 */
	@Test
	public void shouldSetInitialPosition(){
		PlayerWhite player = new PlayerWhite();
		player.setInitialPosition(board);
		for(int i=0; i<8; i++){
			assertEquals(board.getTile(6, i).getPiece().checkType(), "pawn");
		}
		assertEquals(board.getTile(7,7).getPiece().checkType(), "rook");
		assertEquals(board.getTile(7,0).getPiece().checkType(), "rook");
		
		assertEquals(board.getTile(7,2).getPiece().checkType(), "bish");
		assertEquals(board.getTile(7,5).getPiece().checkType(), "bish");
		
		assertEquals(board.getTile(7,4).getPiece().checkType(), "king");
		
		assertEquals(board.getTile(7,3).getPiece().checkType(), "quee");
		
		assertEquals(board.getTile(7,1).getPiece().checkType(), "knig");
		assertEquals(board.getTile(7,6).getPiece().checkType(), "knig");
		
		for(int i=0; i<board.getWidth()-2; i++){
			for(int j=0; j<board.getHeight(); j++){
				assertEquals(board.getTile(i, j).isEmpty(), true);
			}
		}
		
	}

}

package chess;
import chess.Bishop;

import static org.junit.Assert.*;

import org.junit.Test;

public class TileTest {

	/**
	 * test tile constructor 
	 * and the tile should be empty and piece should be null when first initialized
	 */
	@Test
	public void tileInitialzier(){
		Tile tile = new Tile();
		assertTrue(tile.isEmpty());
		assertNull(tile.getPiece());
	}
	
	/**
	 * set a piece for tile
	 * the tile should not be empty now
	 */
	@Test
	public void setTile(){
		Bishop bishop = new Bishop();
		Tile tile = new Tile();
		tile.setPiece(bishop);
		assertFalse(tile.isEmpty());
	}

}

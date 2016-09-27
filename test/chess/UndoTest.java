package chess;

import static org.junit.Assert.*;

import java.util.Stack;

import javax.swing.ImageIcon;

import org.junit.Test;

import chess.Undo.MyNode;

public class UndoTest {

	@Test
	public void initializeUndoStack(){
		Undo stack = new Undo();
		assertTrue(stack.stack instanceof Stack);
		assertEquals(stack.stack.size(), 0);
		assertTrue(stack.empty());
	}
	
	@Test
	public void addNode(){
		Undo stack = new Undo();
		Piece start = new Pawn();
		Piece end = new Pawn();
		Position startPosition = new Position();
		Position endPosition = new Position();
		boolean hasNotMove = false;
		ChessPieceGUI startGUI = new ChessPieceGUI(0, 0, null, null);
		ChessPieceGUI endGUI = new ChessPieceGUI(0, 0, null, null);
		ImageIcon lastImg = new ImageIcon();
		Player player = new PlayerWhite();
		stack.add(start, end, startPosition, endPosition, player, hasNotMove, startGUI, endGUI, lastImg);
		assertEquals(stack.stack.size(), 1);
		MyNode node = stack.pop();
		assertEquals(node.getStart(), start);
		assertEquals(node.getEnd(), end);
		assertEquals(node.getStartPosition(), startPosition);
		assertEquals(node.getEndPosition(), endPosition);
		assertEquals(node.isHasNotMove(), hasNotMove);
		assertEquals(node.getStartGUI(), startGUI);
		assertEquals(node.getEndGUI(), endGUI);
		assertEquals(node.getPlayer(), player);
	}
	
	@Test
	public void clearStack(){
		Undo stack = new Undo();
		Piece start = new Pawn();
		Piece end = new Pawn();
		Position startPosition = new Position();
		Position endPosition = new Position();
		boolean hasNotMove = false;
		ChessPieceGUI startGUI = new ChessPieceGUI(0, 0, null, null);
		ChessPieceGUI endGUI = new ChessPieceGUI(0, 0, null, null);
		ImageIcon lastImg = new ImageIcon();
		Player player = new PlayerWhite();
		stack.add(start, end, startPosition, endPosition, player, hasNotMove, startGUI, endGUI, lastImg);
		assertEquals(stack.stack.size(), 1);
		MyNode node = stack.pop();
		assertEquals(node.getStart(), start);
		assertEquals(node.getEnd(), end);
		assertEquals(node.getStartPosition(), startPosition);
		assertEquals(node.getEndPosition(), endPosition);
		assertEquals(node.isHasNotMove(), hasNotMove);
		assertEquals(node.getStartGUI(), startGUI);
		assertEquals(node.getEndGUI(), endGUI);
		assertEquals(node.getPlayer(), player);
		
		stack.clear();
		assertTrue(stack.empty());
	}

}

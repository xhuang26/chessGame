package chess;

import java.util.Stack;

import javax.swing.ImageIcon;

public class Undo {
	
	protected Stack<MyNode> stack;
	
	public Undo(){
		stack = new Stack<MyNode>();
	}
	
	public void add(Piece start, Piece end, Position startPosition, Position endPosition, Player player, boolean hasNotMove, ChessPieceGUI startGUI, ChessPieceGUI endGUI, ImageIcon lastImg){
		MyNode node = new MyNode(start, end, startPosition, endPosition, player, hasNotMove, startGUI, endGUI, lastImg);
		stack.push(node);
	}
	public MyNode pop(){
		return stack.pop();
	}
	public boolean empty(){
		return stack.empty();
	}
	public void clear(){
		stack.clear();
	}
	public class MyNode{
		private Piece start = null;
		private Piece end = null;
		private Position startPosition = null;
		private Position endPosition = null;
		private Player player = null;
		private boolean hasNotMove = false;
		private ChessPieceGUI startGUI = null;
		private ChessPieceGUI endGUI = null;
		private ImageIcon lastImg = null;
		
		protected MyNode(Piece start, Piece end, Position startPosition, Position endPosition, Player player, boolean hasNotMove, ChessPieceGUI startGUI, ChessPieceGUI endGUI, ImageIcon lastImg){
			this.setStart(start);
			this.setEnd(end);
			this.setStartPosition(startPosition);
			this.setEndPosition(endPosition);
			this.setPlayer(player);
			this.setHasNotMove(hasNotMove);
			this.setStartGUI(startGUI);
			this.setEndGUI(endGUI);
			this.setLastImg(lastImg);
		}

		public Position getEndPosition(){
			return endPosition;
		}
		public void setEndPosition(Position endPosition){
			this.endPosition = endPosition;
		}
		public Piece getStart() {
			return start;
		}

		public void setStart(Piece start) {
			this.start = start;
		}

		public Piece getEnd() {
			return end;
		}

		public void setEnd(Piece end) {
			this.end = end;
		}

		public Position getStartPosition() {
			return startPosition;
		}

		public void setStartPosition(Position startPosition) {
			this.startPosition = startPosition;
		}
		
		public Player getPlayer() {
			return player;
		}

		public void setPlayer(Player player) {
			this.player = player;
		}

		public boolean isHasNotMove() {
			return hasNotMove;
		}

		public void setHasNotMove(boolean hasNotMove) {
			this.hasNotMove = hasNotMove;
		}

		public ChessPieceGUI getStartGUI() {
			return startGUI;
		}

		public void setStartGUI(ChessPieceGUI startGUI) {
			this.startGUI = startGUI;
		}

		public ChessPieceGUI getEndGUI() {
			return endGUI;
		}

		public void setEndGUI(ChessPieceGUI endGUI) {
			this.endGUI = endGUI;
		}

		public ImageIcon getLastImg() {
			return lastImg;
		}

		public void setLastImg(ImageIcon lastImg) {
			this.lastImg = lastImg;
		}
	}
}

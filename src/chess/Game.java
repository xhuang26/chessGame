package chess;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game {
	private Board board;
	private Player playerWhite = null;
	private Player playerBlack = null;
	protected Player nextPlayer = null;
	protected boolean hasUndoChance = false;
	
    //type
    public static final String BISHOP = "bish";
    public static final String ROOK = "rook";
    public static final String KING = "king";
    public static final String QUEEN = "quee";
    public static final String PAWN = "pawn";
    public static final String KNIGHT = "knig";
    public static final String HOPPER = "hopp";
    public static final String ALFIL = "alfi";
    
    
	//different return code for recording the result of one chess move
	public static final int WRONG_TURN = 1;
	public static final int NO_CHESS_AT_SPOT = 2;
	public static final int BREAK_PIECE_RULE = 3;
	public static final int CHECK_MATE = 4;
	public static final int STALE_MATE = 5;
	public static final int NOT_END = 6;
	
	
	
	
	/**
	 * print out the chess board
	 * @param board
	 */
	protected static void printChessBoard(Board board){
		System.out.format("   -    -    -    -    -    -    -    -\n");
		for(int i=0; i<8; i++){
			System.out.format("%d: ", i);
			for(int j=0; j<8; j++){
				Piece piece = board.getTile(i,j).getPiece();
				if(piece != null){
					String type = board.getTile(i,j).getPiece().checkType();
					String name = board.getTile(i,j).getPiece().getOwner().getName();
					System.out.format("%s_%s ", type.substring(0,1), name.substring(0,1));
				} else {
					System.out.format("    ");
				}
				
			}
			System.out.format("\n");
		}
		System.out.format("   -  -  -  -  -  -  -  -\n");
		for(int i=0; i<8; i++){
			System.out.format("%d: ", 8-i);
			for(int j=0; j<8; j++){
				Piece piece = board.getTile(i,j).getPiece();
				if(piece != null){
					int[] pos = board.getTile(i,j).getPiece().getPosition();
					System.out.format("[%d,%d] ", pos[0], pos[1]);
				} else {
					System.out.format("    ");
				}
			}
			System.out.format("\n");
		}
		
	}

	
	/** 
	 * initialize game
	 * @param width
	 * @param height
	 */
	protected Game(int width, int height){
		 Board new_board= new Board(width, height);
		 setBoard(new_board);	
		 this.initPlayers();
	}
	
	/**
	 * set private variable nextPlayer
	 * for test uses
	 * @param nextPlayer
	 */
	protected void setNextPlayer(Player nextPlayer){
		this.nextPlayer = nextPlayer;
	}
	
	/**
	 * set private variable playerWhite
	 * for test uses
	 * @param playerWhite
	 */
	protected void setPlayerWhite(PlayerWhite playerWhite){
		this.playerWhite = playerWhite;
	}
	
	/**
	 * set private variable playerBlack
	 * for test uses
	 * @param playerBlack
	 */
	protected void setPlayerBlack(PlayerBlack playerBlack){
		this.playerBlack = playerBlack;
	}
	
	/**
	 * initialize two players and their pieces on board
	 * @return
	 */
	protected Map<String, Player> initPlayers() {
		this.playerWhite = new PlayerWhite();
		this.playerBlack = new PlayerBlack();
		this.nextPlayer = this.playerWhite;
		playerWhite.setInitialPosition(this.board);
		playerBlack.setInitialPosition(this.board);
		Map<String, Player> players = new HashMap<>();
		players.put("white", this.playerWhite);
		players.put("black", this.playerBlack);
		return players;
	}
	
	protected Board getBoard() {
		return board;
	}
	
	protected void setBoard(Board board) {
		this.board = board;
	}
	
	/**
	 * set a piece at a specific position
	 * if the position is occupied, the current piece will be replaced at the board
	 * set as protected and should be called after checking if the move follow the rule for that piece
	 * @param x
	 * @param y
	 * @param new_piece
	 */
	protected void setPiece(int x, int y, Piece new_piece){
		int[] original_position = new_piece.getPosition();
		Tile start_tile = this.board.getTile(original_position[0], original_position[1]);
		Tile end_tile = this.board.getTile(x,y);
		if(this.board.occupied(x, y)){ // if occupied, set eaten status to true
				Piece prev_piece = end_tile.getPiece();
				prev_piece.setEaten(true);
		}
		//set tile where new_piece was original at to null
		end_tile.setPiece(new_piece);
		start_tile.setPiece(null);
		int[] new_position = {x,y};
		new_piece.setPosition(new_position);
	}
	
	/**
	 * reset cur_piece to position (start_x, start_y) and reset prev_piece to position (end_x, end_y)
	 * @param start_x
	 * @param start_y
	 * @param end_x
	 * @param end_y
	 * @param cur_piece
	 * @param prev_piece
	 */
	protected void resetPiece(int start_x, int start_y, int end_x, int end_y, Piece cur_piece, Piece prev_piece){
		this.board.getTile(start_x,start_y).setPiece(cur_piece);
		int[] start_position = {start_x,start_y};
		cur_piece.setPosition(start_position);
		this.board.getTile(end_x,end_y).setPiece(prev_piece);
		if(prev_piece != null){ //if prev_piece is not null, need to set the status eaten to false and 
			int[] end_position = {end_x, end_y};
			prev_piece.setEaten(false);
			prev_piece.setPosition(end_position);
		}
	}
	
	/**
	 * 1. check if it's a valid step for that specific chess
	 * 2. check if need to eat opposite's piece
	 * 3. check if the step exposed king
	 * @param start_x
	 * @param start_y
	 * @param end_x
	 * @param end_y
	 * @param onlyCheck will be set to true if this function is used for checking if the current move is legal but not actually move it
	 * @return
	 */
	protected boolean move(int start_x, int start_y, int end_x, int end_y, boolean onlyCheck){
		Tile start_tile = this.board.getTile(start_x, start_y);
		Piece cur_piece = start_tile.getPiece();
		boolean pawnChangeBack = false;
		if(cur_piece instanceof Pawn && cur_piece.hasNotMove == true){
			pawnChangeBack = true;
		}
		if(!cur_piece.isValid(end_x, end_y, this.board)){
			if((pawnChangeBack && cur_piece.hasNotMove == false)) {cur_piece.hasNotMove = true;}
			return false;
		}

		Tile end_tile = this.board.getTile(end_x, end_y);
		Piece end_piece = end_tile.getPiece();
		
		if(this.board.getTile(end_x, end_y).getPiece() != null && !this.board.getTile(start_x, start_y).getPiece().ifTakenByOpposite(end_x, end_y, board)){
			if((pawnChangeBack && cur_piece.hasNotMove == false)) {cur_piece.hasNotMove = true;}
			return false;
		}
		this.setPiece(end_x, end_y, cur_piece);
		Player cur_player = cur_piece.getOwner();
		if(this.exposeKing(cur_player)){
			this.resetPiece(start_x, start_y, end_x, end_y, cur_piece, end_piece);
			if((pawnChangeBack && cur_piece.hasNotMove == false)) {cur_piece.hasNotMove = true;}
			return false;
		}
		if(onlyCheck){
			if((pawnChangeBack && cur_piece.hasNotMove == false)) {cur_piece.hasNotMove = true;}
			this.resetPiece(start_x, start_y, end_x, end_y, cur_piece, end_piece);
		}
		return true;
	}
	
	/**
	 * check if the current user's king is in check
	 * @param cur_player
	 * @return
	 */
	protected boolean exposeKing(Player cur_player){
		Player opposite_player = cur_player==this.playerWhite ? this.playerBlack:this.playerWhite;
		ArrayList<Piece> opposite_pieces = opposite_player.getPieces();
		Piece cur_king = cur_player.getKing();
		int[] cur_king_position = cur_king.getPosition();
		for(Piece piece : opposite_pieces){
			if(piece.isEaten()){
				continue;
			}

			if(piece.isValid(cur_king_position[0], cur_king_position[1], board)){
				return true;
			}
		}
		return false;
	}
	
	
	private boolean kingSaveItself(Piece king){
		//check if the king can move to other place to save itself
		ArrayList<int[]> king_available_moves = king.availableMove(this.board);
		if(king_available_moves.size() != 0){
			for(int[] pos : king_available_moves){
				if(this.move(king.getPosition()[0], king.getPosition()[1], pos[0], pos[1], true)){
					
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean kingSavedByOthers(Player cur_player){
		ArrayList<Piece> cur_pieces = cur_player.getPieces();
		for(Piece piece : cur_pieces){
			if(piece.isEaten()) {continue;}
			ArrayList<int[]>available_moves = piece.availableMove(this.board);
			int[] start_position = piece.getPosition();
			for(int[] end_position : available_moves){
				if(this.move(start_position[0], start_position[1], end_position[0], end_position[1], true)){
					return true;
				}
			}
		}
		return false;
	}
	
	

	
	/**
	 * check if the game can be end
	 * @param nextPlayer
	 * @return  status code for the current move's result, it can be STALE_MATE or CHECK_MATE when need to end and it should be NOT_END when it didn't statisfy the ending condition
	 */
	protected int endGame(Player nextPlayer){
		int status = NOT_END;
		Piece king = nextPlayer.getKing();
		if(!kingSaveItself(king) && !kingSavedByOthers(nextPlayer)){
			if(this.exposeKing(nextPlayer)){
				status = CHECK_MATE;
			} else {
				status = STALE_MATE;
			}
		}
		//this.printChessBoard(this.board);
		return status;
	}
	
	/**
	 * will make move on board if the move is not breaking any rule for the chess game
	 * will return status code to notify the result of current move
	 * @param start_x
	 * @param start_y
	 * @param end_x
	 * @param end_y
	 * @return
	 */
	public int makeMove(Position start, Position end){
		int start_x = start.getPosition()[0];
		int start_y = start.getPosition()[1];
		int end_x = end.getPosition()[0];
		int end_y = end.getPosition()[1];
		Player player = this.board.getTile(start_x, start_y).getPiece().getOwner();
		if(player != nextPlayer){
			//System.out.format("not your turn...\n");
			return WRONG_TURN;
		}
		if(this.board.getTile(start_x, start_y).getPiece() == null){
			//System.out.format("no chess available\n");
			return NO_CHESS_AT_SPOT;
		}
		if(!this.move(start_x, start_y, end_x, end_y, false)){
			//System.out.format("invalid move: [%d,%d]->[%d,%d]\n", start_x, start_y, end_x, end_y);
			return BREAK_PIECE_RULE;
		} 
		this.nextPlayer = this.nextPlayer == this.playerWhite? this.playerBlack:this.playerWhite;
		this.hasUndoChance = true;
		return endGame(nextPlayer);
	}
	
}

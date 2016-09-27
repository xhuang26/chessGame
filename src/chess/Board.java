package chess;


public class Board {
	private Tile[][] grid;
	private final int width;
	private final int height;

	/**
	 * initialize board with width and height
	 * throw exception if input wrong dimension
	 * @param width
	 * @param height
	 */
	protected Board(int width, int height){
		if(width < 0 || height <0){
			throw new OutOfValidRange("wong dimension for board");
		}
		this.width = width;
		this.height = height;
		this.grid = new Tile[width][height];
		for(int i=0; i<width; i++){
			for(int j=0; j<height; j++){
				this.grid[i][j] = new Tile();
			}
		}
	}
	
	protected Tile getTile(int x, int y){
		return this.grid[x][y];
	}
	
	/**
	 * check if the current tile is occupied, no matter which player it belongs to
	 * @param x
	 * @param y
	 * @return
	 */
	protected boolean occupied(int x, int y){
		if(x<0 || y<0 || x>=this.getWidth() || y>=this.getHeight()){
			throw new OutOfValidRange("pass in invalid index");
		}
		if(this.grid[x][y].isEmpty()){
			return false;
		}
		return true;
	}

	protected int getWidth() {
		return width;
	}
	protected int getHeight() {
		return height;
	}
	

}

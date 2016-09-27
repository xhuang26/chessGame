package chess;

public class Position {
	private int x;
	private int y;
	private boolean isClear = true;
	
	public Position(){
		this.x = -1;
		this.y = -1;
	}
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	protected int[] getPosition(){
		int[] pos = {x, y};
		return pos;
	}
	protected int getX(){
		return x;
	}
	
	protected int getY(){
		return y;
	}
	protected void setPosition(int x, int y){
		if(x <0 || y<0){
			throw new OutOfValidRange("invalid x, y value");
		}
		this.x = x;
		this.y = y;
		this.isClear = false;
	}
	protected void setPosition(int[] pos){
		if(pos.length != 2){
			throw new OutOfValidRange("position is not a int array of length 2");
		}
		if(pos[0] <0 || pos[1]<0){
			throw new OutOfValidRange("invalid x, y value");
		}
		this.x=pos[0];
		this.y=pos[1];
		this.isClear = false;
	}
	
	protected void clearPostion(){
		this.x  = -1;
		this.y = -1;
		this.isClear = true;
	}
	public boolean isClear() {
		return isClear;
	}
	
	public Position getYX(){
		Position yx = new Position(this.y, this.x);
		return yx;
	}
}

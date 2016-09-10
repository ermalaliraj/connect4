package com.ea.connect4.canvas.cmd;


/**
 * Command used by the Canvas for filling the area around a given coordinate.
 * Same as "bucket fill" tool in paint programs.
 * 
 * @Ermal Aliraj
 */
public class CommandBucketFill extends Command{

	private int x;
	private int y;
	private String color;
	
	public CommandBucketFill() {
		name = CMD_FILL;
	}
	
	public CommandBucketFill(int x, int y, String color) {
		this();
		this.x = x;
		this.y = y;
		this.color = color;
		
	}

	public String getName() {
		return name;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String getColor() {
		return color;
	}
	
}
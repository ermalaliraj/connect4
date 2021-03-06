package com.ea.connect4.canvas.cmd;


/**
 * Represents the command for drawing a line in the canvas.
 * A line is represented by two points connected together.
 * Line from (x1,y1) to (x2,y2). 
 */
public class CommandLine extends Command{
	
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	public CommandLine(int x1, int y1, int x2, int y2) {
		this();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public CommandLine() {
		name = CMD_LINE;
	}

	public String getName() {
		return name;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}
	
	public String toString() {
		String rv = "CommandLine[("+x1+", "+y1+"), ("+x2+", "+y2+")]";
		return rv;
	}
	
}
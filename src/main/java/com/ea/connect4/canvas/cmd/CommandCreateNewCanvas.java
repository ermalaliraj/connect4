package com.ea.connect4.canvas.cmd;


/**
 * Command used by the canvas for creating a new Canvas
 * 
 * @Ermal Aliraj
 */
public class CommandCreateNewCanvas extends Command{
	
	private int width;
	private int height;
	
	public CommandCreateNewCanvas(int w, int h) {
		this();
		width = w;
		height = h;
	}
	
	public CommandCreateNewCanvas() {
		name = CMD_CREATE_CANVAS;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getName() {
		return name;
	}
	
}

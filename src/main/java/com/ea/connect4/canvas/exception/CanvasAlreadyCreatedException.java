package com.ea.connect4.canvas.exception;

/**
 * Thrown when try to create a new Canvas for the user.
 * From Front-end side will be catched and use to show the dialog to the user like:
 * "Are you sure you want to restart the game?". If the user click YES then will call first deleteCanvas()
 * then newCanvas().
 *  
 * @author Ermal Aliraj
 *
 */
public class CanvasAlreadyCreatedException extends CanvasException{

	private static final long serialVersionUID = -8629000422659650854L;

	public CanvasAlreadyCreatedException(String message){
		super(message);
	}
	
	public CanvasAlreadyCreatedException(String message, Throwable e){
		super(message,e);
	}
}

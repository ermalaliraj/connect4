package com.ea.connect4.canvas.exception;

/**
 * Thrown when you try to do an operation without first creating a canvas for the specific user
 *  
 * @author Ermal Aliraj
 *
 */
public class NoCanvasException extends CanvasException{

	private static final long serialVersionUID = -8629000422659650854L;

	public NoCanvasException(String message){
		super(message);
	}
	
	public NoCanvasException(String message, Throwable e){
		super(message,e);
	}
}

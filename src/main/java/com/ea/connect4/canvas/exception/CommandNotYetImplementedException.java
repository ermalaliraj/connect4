package com.ea.connect4.canvas.exception;


/**
 * Thrown when calling a command or operation not supported.
 * Example when trying to draw a line that is not horizontal, not vertical.
 *  
 * @author Ermal Aliraj
 *
 */
public class CommandNotYetImplementedException extends CanvasException{

	private static final long serialVersionUID = -8629000422659650854L;

	public CommandNotYetImplementedException(String message){
		super(message);
	}
	
	public CommandNotYetImplementedException(String message, Throwable e){
		super(message,e);
	}
}

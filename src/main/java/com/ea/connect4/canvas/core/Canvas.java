package com.ea.connect4.canvas.core;

import com.ea.connect4.api.CanvasDTO;
import com.ea.connect4.canvas.cmd.CommandBucketFill;
import com.ea.connect4.canvas.cmd.CommandLine;
import com.ea.connect4.canvas.cmd.CommandRectangle;
import com.ea.connect4.canvas.exception.CanvasException;

/**
 * Contract for Canvas implementation.
 * 
 * @Ermal Aliraj
 * 
 */
public interface Canvas {
	
	void insertLine(CommandLine cmd) throws CanvasException;

	void insertRectangle(CommandRectangle cmd) throws CanvasException;

	void fillBucket(CommandBucketFill cmd) throws CanvasException;
	
	String[][] getMatrix() throws CanvasException;
	
	CanvasDTO getCanvasStateAsDTO()  throws CanvasException;

}

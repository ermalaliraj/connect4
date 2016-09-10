package com.ea.connect4.canvas.cmd;

/**
 * Each command must extends this class so must provide at least the name.
 * 
 * @author Ermal ALiraj
 *
 */
public abstract class Command {
	
	public final static String CMD_CREATE_CANVAS = "C";
	public final static String CMD_LINE       = "L";
	public final static String CMD_RECTANGLE  = "R";
	public final static String CMD_FILL       = "F";
	public final static String CMD_HELP       = "H";
	
	protected String name;

	public abstract String getName();

}

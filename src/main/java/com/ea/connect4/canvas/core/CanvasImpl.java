package com.ea.connect4.canvas.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ea.connect4.api.CanvasDTO;
import com.ea.connect4.canvas.cmd.CommandBucketFill;
import com.ea.connect4.canvas.cmd.CommandLine;
import com.ea.connect4.canvas.cmd.CommandRectangle;
import com.ea.connect4.canvas.exception.CanvasException;
import com.ea.connect4.canvas.exception.CommandNotYetImplementedException;
import com.ea.connect4.canvas.exception.CommandWrongParamsException;
import com.ea.connect4.canvas.util.CanvasCostant;

/**
 * Implementation of the canvas.
 * 
 * @author Ermal Aliraj
 *
 */
public class CanvasImpl implements Canvas {
	
	protected static final transient Log logger = LogFactory.getLog(CanvasImpl.class);
	
	private int width;
	private int height;
	private String matrix[][];
		
	/**
	 * Create an empty Canvas (W)eight x (H)eight
	 */
	public CanvasImpl (int w, int h) throws CanvasException {
		if(width*height > 1000) {
			throw new CommandWrongParamsException("Canvas can be at last 1.000 cells, example 100x100");
		}
		
		this.width = w;
		this.height = h; 
		this.matrix = new String[height][width];
	
		//initialize the empty matrix
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				this.matrix[i][j] = " ";
			}
		}
	}
	
	//
	public CanvasImpl (int w, int h, String matrix[][]) {
		this.width = w;
		this.height = h; 
		this.matrix = matrix;
	}
	public CanvasImpl(CanvasDTO dto) {
		this(dto.getWidth(), dto.getHeight(), dto.getMatrix());
	}
	
	public CanvasDTO getCanvasStateAsDTO(){
		CanvasDTO dto = new CanvasDTO();
		dto.setWidth(getWidth());
		dto.setHeight(getHeight());
		dto.setMatrix(getMatrix());
		return dto;
	}

	/**
	 * Print the canvas state surrounded by the borders: 
	 * --------------    						
	 * | matrix[][] |    						
	 * --------------
	 */
	public void printCanvas() {
		// first row ----------
		for (int j = 0; j < width + 2; j++) {
			System.out.print("-");
		}
		System.out.println();

		// the body of the canvas | **** |
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width + 2; j++) {
				if (j == 0 || j == width + 1) {
					System.out.print("|");
				} else {
					System.out.print(matrix[i][j - 1]);
				}
			}
			System.out.println();
		}

		// footer --------
		for (int j = 0; j < width + 2; j++) {
			System.out.print("-");
		}
		System.out.println("\n");
	}
	
	/**
	 * Insert a Line in the canvas. </br>
	 * 1. Validate the input </br>
	 * 2. Insert the line  </br>
	 * 
	 * @param cmd Command containing x1, y1, x2, y2 for inserting new line. 
	 * @throws CanvasException
	 */
	public void insertLine(CommandLine cmd) throws CanvasException {
		
		validateInputNewLine(cmd);
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				//horizontal line from X1 to X2 (shift +1 to match user input)
				if((j+1)>=cmd.getX1() && (j+1)<=cmd.getX2() && (i+1)==cmd.getY1()){
					matrix[i][j] = CanvasCostant.PIXEL;
				}
				//vertical line from Y1 to Y2 (shift +1 to match user input)
				if((i+1)>=cmd.getY1() && (i+1)<=cmd.getY2() && (j+1)==cmd.getX1()){
					matrix[i][j] = CanvasCostant.PIXEL;
				} 
			}
		}
	}

	private void validateInputNewLine(CommandLine cmd) throws CanvasException {
		if(cmd.getX1() > cmd.getX2() || cmd.getY1() > cmd.getY2()){
			logger.warn("InsertLine - Wrong coordinates: ("+cmd.getX1()+", "+cmd.getY1()+") ("+cmd.getX2()+", "+cmd.getY2()+")");
			throw new CommandWrongParamsException("The coordinates must be congruent: (x1 < x2 && y1 < y2)");
		}
		
		// if Y1!=Y2 than X1 must be equal to X2 => case of vertical line
		// if X1!=X2 than Y1 must be equal to Y2 => case of horizontal line
		// X1=X2 and Y1=Y2 => case of a line with a single point (single '*')
		if(   !( (cmd.getX1()==cmd.getX2() && cmd.getY1()!=cmd.getY2())
			|| (cmd.getY1()==cmd.getY2() && cmd.getX1()!=cmd.getX2()))
			&& !(cmd.getX1()==cmd.getX2() && cmd.getY1()==cmd.getY2())
			){
			logger.warn("InsertLine - Wrong coordinates: ("+cmd.getX1()+", "+cmd.getY1()+") ("+cmd.getX2()+", "+cmd.getY2()+")");
			throw new CommandNotYetImplementedException("You can draw only orizzontal lines: (x1==x2 || y1 == y2)");
		}
		
		if (cmd.getX1()<=0 || cmd.getX2() > getWidth() 
			|| cmd.getY1()<=0 || cmd.getY2() > getHeight()){
			logger.warn("InsertLine - Wrong coordinates: ("+cmd.getX1()+", "+cmd.getY1()+") ("+cmd.getX2()+", "+cmd.getY2()+")");
			throw new CommandWrongParamsException("The coordinates must be inside the canvas: (x > 0 && x <= "+width+") && (y > 0 && y <= "+height+")");
		}
	}

	/**
	 * Insert a Rectangle in the canvas. </br>
	 * 1. Validate the input </br>
	 * 2. Insert the Rectangle  </br>
	 * 
	 * @param cmd Command containing x1, y1, x2, y2 for inserting a rectangle, whose upper left corner is (x1,y1) and lower right corner is (x2,y2)
	 * @throws CanvasException 
	 */
	public void insertRectangle(CommandRectangle cmd) throws CanvasException  {
		
		validateInputRectangle(cmd);
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				//horizontal line from X1 to X2 (shift +1 to match user input)
				//in case we are in the first row(Y1) or in the second row(Y2)
				if((j+1)>=cmd.getX1() && (j+1)<=cmd.getX2() 
						&& ((i+1)==cmd.getY1() || (i+1)==cmd.getY2())){
					matrix[i][j] = CanvasCostant.PIXEL;
				}
				
				//vertical line from Y1 to Y2 (shift +1 to match user input)
				//where we are in the first column(X1) or second column(X2)
				if((i+1)>=cmd.getY1() && (i+1)<=cmd.getY2() 
						&& ((j+1)==cmd.getX1() || (j+1)==cmd.getX2())){
					matrix[i][j] = CanvasCostant.PIXEL;
				} 
			}
		}
	}
	
	private void validateInputRectangle(CommandRectangle cmd) throws CanvasException {
		if(cmd.getX1() > cmd.getX2() || cmd.getY1() > cmd.getY2()){
			logger.warn("InserRectangle - Wrong coordinates: ("+cmd.getX1()+", "+cmd.getY1()+") ("+cmd.getX2()+", "+cmd.getY2()+")");
			throw new CommandWrongParamsException("The coordinates must be congruent: (x1 < x2 && y1 < y2)");
		}
		
		if (cmd.getX1()<=0 || cmd.getX2() > getWidth() 
			|| cmd.getY1()<=0 || cmd.getY2() > getHeight()){
			logger.warn("InserRectangle - Wrong coordinates: ("+cmd.getX1()+", "+cmd.getY1()+") ("+cmd.getX2()+", "+cmd.getY2()+")");
			throw new CommandWrongParamsException("The coordinates must be inside the canvas: (x > 0 && x <="+width+") && (y > 0 && y <= "+height+")");
		}
	}
	
	/**
	 * Fill the area connected to the coordinate passed as parameter.
	 * 
	 * @param cmd Command containing the "pixel" (x, y) where to start for "filling the bucket"
	 * @throws CanvasException
	 */
	public void fillBucket(CommandBucketFill cmd) throws CanvasException {
		validateInputFillBucket(cmd);

		fillSinglePixelAndExpand(cmd.getX()-1, cmd.getY()-1, cmd.getColor());
	}
	
	/**
	 * The strategy to fill the area is the following:
	 * 1. Insert the "pixel" in the current coordinate.
	 * 2. Do the same with 4 coordinates adjacent to the actual; call recursively the method.
	 * 3. Stop when finding borders or a coordinate already "painted" with 'x' or 'color'
	 *   
	 * @param x coordinate x
	 * @param y coordinate y
	 * @param color color to use for painting the area. Can be any single character as String
	 * @throws CanvasException
	 */
	private void fillSinglePixelAndExpand(int x, int y, String color) throws CanvasException {
		//Stops when finds borders
		if(x<0 || x>=width || y<0 || y>=height){
			return;
		}
		//stops where find other pixel inserted
		if(matrix[y][x].equals(CanvasCostant.PIXEL) || matrix[y][x].equals(color)){
			return;
		}
	
		matrix[y][x] = color;
//		printStatusCanvas();
		
		fillSinglePixelAndExpand(x+1, y, color);
		fillSinglePixelAndExpand(x, y+1, color);
		fillSinglePixelAndExpand(x-1, y, color);
		fillSinglePixelAndExpand(x, y-1, color);
	}
	
	private void validateInputFillBucket(CommandBucketFill cmd) throws CanvasException {
		if (cmd.getX()<=0 || cmd.getX() > getWidth() 
			|| cmd.getY()<=0 || cmd.getY() > getHeight()){
			logger.warn("FillBucket - Wrong coordinates: ("+cmd.getX()+", "+cmd.getY()+")");
			throw new CommandWrongParamsException("The coordinates must be inside the canvas: (x > 0 && x <= "+width+") && (y > 0 && y <= "+height+")");
		}
		
		if(cmd.getColor() == null){
			logger.warn("FillBucket - Color can not be null. ");
			throw new CommandWrongParamsException("Choose a color!");
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	public String[][] getMatrix() {
		return matrix;
	}
	public void printMatrix() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(matrix[i][j]);	
			}
			System.out.println();
		}
	}
}

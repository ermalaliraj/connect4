package com.ea.connect4.be.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ea.connect4.api.CanvasDTO;
import com.ea.connect4.api.FillBucketInputDTO;
import com.ea.connect4.api.InsertRectangleInputDTO;
import com.ea.connect4.api.NewCanvasInputDTO;
import com.ea.connect4.canvas.exception.CanvasException;
import com.ea.connect4.canvas.exception.CommandWrongParamsException;
import com.ea.connect4.service.CanvasService;

/**
 * Class to test the BucketFiller Command. 
 * All the tests start with a Canvas already initialized to the following state (ignore numbers): 
 	----------------------
	|xxxxxxxx            |
	|x      x      2     |
	|x  1   x            |
	|x      xxxxxxxx     |
	|xxxxxxxx  3   x     |
	----------------------
 
 * @author Ermal Aliraj
 *
 */
public class CommandFillTest extends AbstractSpringTest{

	protected static final transient Log logger = LogFactory.getLog(CommandFillTest.class);
	public final static String COLOR = "o";
	
	String userId = "0001";
	int width = 20;
	int height = 5;
	
	@Autowired
	CanvasService canvasService;
	
	/**
	 * Create an empty canvas for the user
	 */
	@Before
	public void setUp() {
		NewCanvasInputDTO in = new NewCanvasInputDTO(userId, width, height);
		try {
			canvasService.newCanvas(in);
			
			InsertRectangleInputDTO inCmd = new InsertRectangleInputDTO(userId, 1, 1, 8, height);
			canvasService.insertRectangle(inCmd);

			inCmd = new InsertRectangleInputDTO(userId, 8, height-1, 15, height-1);
			canvasService.insertRectangle(inCmd);
			
			inCmd = new InsertRectangleInputDTO(userId, 15, height-1, 15, height);
			canvasService.insertRectangle(inCmd);

		} catch (CanvasException e) {
			logger.error("Cannot initialize the canvas!", e);
		}
	}
	
	/**
	 * Quit the game. Delete the canvas for the user. 
	 * Show last state of the canvas before delete it.
	 */
	@After
	public void drillDown() {
		try {
			CanvasDTO canvas = canvasService.getCanvasByUserId(userId);
			System.out.println("Canvas dopo l'aggiornamento: \n"+canvas);
			System.out.println("");
			
			canvasService.deleteCanvasByUserId(userId);
		} catch (CanvasException e) {
			logger.error("Cannot quit the game. Canvas still not empty for user "+userId, e);
		}
	}
	
	/**
	 * Check correctness of the filler when starting point is as follow: 
	 	----------------------
		|xxxxxxxxO           |
		|x      x            |
		|x      x            |
		|x      xxxxxxxx     |
		|xxxxxxxx      x     |
		----------------------
	 * Count and compare the number of colored pixels.
	 */
	@Test
	public void testHP_FillArea2LeftUpperStartingPoint() throws CanvasException {
		FillBucketInputDTO cmd2 = new FillBucketInputDTO(userId, 9, 1, COLOR);
		CanvasDTO canvas = canvasService.fillArea(cmd2);
	
		int expected = (12*3)+(5*2);
		assertExpected(canvas, COLOR, expected);
	}
	
	/**
	 * Check correctness of the filler when starting point is as follow:  
	 	----------------------
		|xxxxxxxx           O|
		|x      x            |
		|x      x            |
		|x      xxxxxxxx     |
		|xxxxxxxx      x     |
		----------------------
	 * Count and compare the number of colored pixels.
	 */
	@Test
	public void testHP_FillArea2RightUpperStartingPoint() throws CanvasException {
		FillBucketInputDTO cmd2 = new FillBucketInputDTO(userId, 20, 1, COLOR);
		CanvasDTO canvas = canvasService.fillArea(cmd2);
		
		int expected = (12*3)+(5*2);
		assertExpected(canvas, COLOR, expected);
	}
	
	/**
	 * Check correctness of the filler when starting point is as follow:  
	 	----------------------
		|xxxxxxxx            |
		|x      x            |
		|x      xO           |
		|x      xxxxxxxx     |
		|xxxxxxxx      x     |
		----------------------
	 * Count and compare the number of colored pixels.
	 */
	@Test
	public void testHP_FillArea2LeftDownStartingPoint() throws CanvasException {
		FillBucketInputDTO cmd2 = new FillBucketInputDTO(userId, 9, 3, COLOR);
		CanvasDTO canvas = canvasService.fillArea(cmd2);
		
		int expected = (12*3)+(5*2);
		assertExpected(canvas, COLOR, expected);
	}
	
	/**
	 * Check correctness of the filler when starting point is as follow:  
	 	----------------------
		|xxxxxxxx            |
		|x      x            |
		|x      x            |
		|x      xxxxxxxxO    |
		|xxxxxxxx      x     |
		----------------------
	 * Count and compare the number of colored pixels.
	 */
	@Test
	public void testHP_FillArea2EdgeStartingPoint() throws CanvasException {
		FillBucketInputDTO cmd2 = new FillBucketInputDTO(userId, 16, 4, COLOR);
		CanvasDTO canvas = canvasService.fillArea(cmd2);
		
		int expected = (12*3)+(5*2);
		assertExpected(canvas, COLOR, expected);
	}
	
	/**
	 * Check correctness of the filler when starting point is as follow:  
	 	----------------------
		|xxxxxxxx            |
		|x      x            |
		|x      x            |
		|x      xxxxxxxx     |
		|xxxxxxxx      x    O|
		----------------------
	 * Count and compare the number of colored pixels.
	 */
	@Test
	public void testHP_FillArea2RightDownStartingPoint() throws CanvasException {
		FillBucketInputDTO cmd2 = new FillBucketInputDTO(userId, 20, 5, COLOR);
		CanvasDTO canvas = canvasService.fillArea(cmd2);
		
		int expected = (12*3)+(5*2);
		assertExpected(canvas, COLOR, expected);
	}

	/**
	 * Check correctness (Area 3) of the filler when starting point is as follow:  
	 	----------------------
		|xxxxxxxx            |
		|x      x            |
		|x      x            |
		|x      xxxxxxxx     |
		|xxxxxxxx  O   x     |
		----------------------
	 * Count and compare the number of colored pixels.
	 */
	@Test
	public void testHP_FillArea3() throws CanvasException {
		FillBucketInputDTO cmd2 = new FillBucketInputDTO(userId, 11, 5, COLOR);
		CanvasDTO canvas = canvasService.fillArea(cmd2);
		
		int expected = 6;
		assertExpected(canvas, COLOR, expected);
	}
	
	/**
	 * Check correctness (Area 1) of the filler when starting point is as follow:  
	 	----------------------
		|xxxxxxxx            |
		|x      x            |
		|x   O  x            |
		|x      xxxxxxxx     |
		|xxxxxxxx      x     |
		----------------------
	 * Count and compare the number of colored pixels.
	 */
	@Test
	public void testHP_FillArea1() throws CanvasException {
		FillBucketInputDTO cmd2 = new FillBucketInputDTO(userId, 5, 3, COLOR);
		CanvasDTO canvas = canvasService.fillArea(cmd2);
		
		int expected = 6*3;
		assertExpected(canvas, COLOR, expected);
	}	
	
	//Check exceptions
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_XZero() throws CanvasException {
		FillBucketInputDTO cmd2 = new FillBucketInputDTO(userId, 0, 3, COLOR);
		canvasService.fillArea(cmd2);
	}
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_XLessThanZero() throws CanvasException {
		FillBucketInputDTO cmd2 = new FillBucketInputDTO(userId, -1, 3, COLOR);
		canvasService.fillArea(cmd2);
	}
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_YZero() throws CanvasException {
		FillBucketInputDTO cmd2 = new FillBucketInputDTO(userId, 7, 0, COLOR);
		canvasService.fillArea(cmd2);
	}
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_YLessThanZero() throws CanvasException {
		FillBucketInputDTO cmd2 = new FillBucketInputDTO(userId, 7, -1, COLOR);
		canvasService.fillArea(cmd2);
	}
}

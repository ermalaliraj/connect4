package com.ea.connect4.be.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ea.connect4.api.CanvasDTO;
import com.ea.connect4.api.InsertRectangleInputDTO;
import com.ea.connect4.api.NewCanvasInputDTO;
import com.ea.connect4.canvas.exception.CanvasException;
import com.ea.connect4.canvas.exception.CommandWrongParamsException;
import com.ea.connect4.service.CanvasService;

public class CommandRectangleTest extends AbstractSpringTest{

	protected static final transient Log logger = LogFactory.getLog(CommandRectangleTest.class);
	
	@Autowired
	CanvasService canvasService;
	
	String userId = "0001";
	int width = 20;
	int height = 4;
	
	/**
	 * Create an empty canvas for the user
	 */
	@Before
	public void setUp() {
		NewCanvasInputDTO in = new NewCanvasInputDTO(userId, width, height);
		try {
			canvasService.newCanvas(in);
		} catch (CanvasException e) {
			logger.error("Cannot initialize the canvas!", e);
		}
	}
	
	/**
	 * Quit the game. Delete the canvas for the user
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
	 * Inserts a rectangle which covers the borders of the canvas.
	 * Tests the presence of the starts all around the borders and counts the total number of stars.
	 */
	@Test
	public void testHP_CoverBordersWithStars() throws CanvasException {
		int x1=1, y1=1, x2=width, y2=height;
		InsertRectangleInputDTO in = new InsertRectangleInputDTO(userId, x1, x1, width, height);
		CanvasDTO canvas = canvasService.insertRectangle(in);
		
		int expected = (x2-x1)*2 + (y2-y1)*2;
		assertExpected(canvas, expected);
	}

	//Will be a line
	@Test
	public void testCL_X1EqualX2() throws CanvasException {
		int x1=3, y1=1, x2=3, y2=4;
		InsertRectangleInputDTO in = new InsertRectangleInputDTO(userId, x1, y1, x2, y2);
		CanvasDTO canvas = canvasService.insertRectangle(in);
		
		int expected = (y2-y1) + 1;
		assertExpected(canvas, expected);
	}
	
	//Will be a line
	@Test
	public void testCL_Y1EqualY2() throws CanvasException {
		int x1=1, y1=1, x2=14, y2=1;
		InsertRectangleInputDTO in = new InsertRectangleInputDTO(userId, x1, y1, x2, y2);
		CanvasDTO canvas = canvasService.insertRectangle(in);
		
		int expected = (x2-x1) + 1;
		assertExpected(canvas, expected);
	}
	
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_X1GreaterThanX2() throws CanvasException {
		InsertRectangleInputDTO in = new InsertRectangleInputDTO(userId, 16, 1, 14, 3);
		canvasService.insertRectangle(in);
	}
	
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_X1LessThanZero() throws CanvasException {
		InsertRectangleInputDTO in = new InsertRectangleInputDTO(userId, -1, 1, 20, 3);
		canvasService.insertRectangle(in);
	}
	
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_X1EqualZero() throws CanvasException {
		InsertRectangleInputDTO in = new InsertRectangleInputDTO(userId, 0, 1, 20, 3);
		canvasService.insertRectangle(in);
	}
	
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_X2GreaterThanWidth() throws CanvasException {
		InsertRectangleInputDTO in = new InsertRectangleInputDTO(userId, 16, 1, width+1, 3);
		canvasService.insertRectangle(in);
	}

	@Test
	public void testCL_X2EqualWidth() throws CanvasException {
		int x1=16, y1=1, x2=width, y2=4;
		InsertRectangleInputDTO in = new InsertRectangleInputDTO(userId, x1, y1, x2, y2);
		CanvasDTO canvas = canvasService.insertRectangle(in);
		
		int expected = (x2-x1)*2 + (y2-y1)*2;
		assertExpected(canvas, expected);
	}
	
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_YLessThanZero() throws CanvasException {
		InsertRectangleInputDTO in = new InsertRectangleInputDTO(userId, 16, -1, 20, 3);
		canvasService.insertRectangle(in);
	}

	@Test(expected=CommandWrongParamsException.class)
	public void testEX_YEqualZero() throws CanvasException {
		InsertRectangleInputDTO in = new InsertRectangleInputDTO(userId, 16, 0, 20, 3);
		canvasService.insertRectangle(in);
	}
	
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_YGreaterThanHeight() throws CanvasException {
		InsertRectangleInputDTO in = new InsertRectangleInputDTO(userId, 1, 1, 6, height+1);
		canvasService.insertRectangle(in);
	}
	
	//vertical lines
	
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_Y1GreaterThanY2() throws CanvasException {
		InsertRectangleInputDTO in = new InsertRectangleInputDTO(userId, 16, 3, 20, 1);
		canvasService.insertRectangle(in);
	}
	
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_Y1LessThanZero() throws CanvasException {
		InsertRectangleInputDTO in = new InsertRectangleInputDTO(userId, 16, -1, 20, 3);
		canvasService.insertRectangle(in);
	}

	@Test(expected=CommandWrongParamsException.class)
	public void testEX_Y1EqualZero() throws CanvasException {
		InsertRectangleInputDTO in = new InsertRectangleInputDTO(userId, 16, 0, 20, 3);
		canvasService.insertRectangle(in);
	}
	
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_Y2GreaterThanHeight() throws CanvasException {
		InsertRectangleInputDTO in = new InsertRectangleInputDTO(userId, 16, 1, 20, height+1);
		canvasService.insertRectangle(in);
	}
	
	@Test
	public void testCL_Y2EqualHeight() throws CanvasException {
		int x1=16, y1=1, x2=20, y2=height;
		InsertRectangleInputDTO in = new InsertRectangleInputDTO(userId, x1, y1, x2, y2);
		CanvasDTO canvas = canvasService.insertRectangle(in);
		
		int expected = (x2-x1)*2 + (y2-y1)*2;
		assertExpected(canvas, expected);
	}	
	
}

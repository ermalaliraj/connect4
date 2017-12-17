package com.ea.connect4.be.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ea.examples.canvas.exception.CanvasException;
import com.ea.examples.canvas.exception.CommandNotYetImplementedException;
import com.ea.examples.canvas.exception.CommandWrongParamsException;
import com.ea.examples.connect4.api.CanvasDTO;
import com.ea.examples.connect4.api.InsertLineInputDTO;
import com.ea.examples.connect4.api.NewCanvasInputDTO;
import com.ea.examples.connect4.service.CanvasService;

public class CommandLineTest extends AbstractSpringTest {

	protected static final transient Log logger = LogFactory.getLog(CommandLineTest.class);
	
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
	 * Inserts 4 lines which covers the borders of the canvas.
	 * Upper row a), left column b), right column c) and down row d).
	 * On each insert tests the number of pixels ('x') present in the canvas, 
	 * and the ownership of the user.
	 */
	@Test
	public void testHP_CoverBordersWithLines() throws CanvasException {
		InsertLineInputDTO in = new InsertLineInputDTO();
		in.setUserId(userId);
		in.setX1(1);
		in.setY1(1);
		in.setX2(width);
		in.setY2(1);
		
		// a.1. insert line
		CanvasDTO canvas = canvasService.insertLine(in);
		// a.2. get canvas from DB
		CanvasDTO canvasDB = canvasService.getCanvasByUserId(userId);
		// a.3. assert equality 
		assertEquals(canvas.getUserId(), canvasDB.getUserId());
		assertEquals(canvas.getWidth(), canvasDB.getWidth());
		assertEquals(canvas.getHeight(), canvasDB.getHeight());
		assertNotNull(canvas.getMatrix());
		assertEquals(canvas.getMatrix().length, canvasDB.getMatrix().length);
		assertEquals(countXInCanvas(canvas), countXInCanvas(canvasDB));
		
		// b.1. insert left vertical line
		in = new InsertLineInputDTO();
		in.setUserId(userId);
		in.setX1(1);
		in.setY1(1);
		in.setX2(1);
		in.setY2(height);
		canvas = canvasService.insertLine(in);
		// b.2. get canvas from DB
		canvasDB = canvasService.getCanvasByUserId(userId);
		// b.3. assert equality 
		assertEquals(canvas.getUserId(), canvasDB.getUserId());
		assertEquals(canvas.getWidth(), canvasDB.getWidth());
		assertEquals(canvas.getHeight(), canvasDB.getHeight());
		assertNotNull(canvas.getMatrix());
		assertEquals(canvas.getMatrix().length, canvasDB.getMatrix().length);
		assertEquals(countXInCanvas(canvas), countXInCanvas(canvasDB));

		// c.1. insert right vertical line
		in = new InsertLineInputDTO();
		in.setUserId(userId);
		in.setX1(width);
		in.setY1(1);
		in.setX2(width);
		in.setY2(height);
		canvas = canvasService.insertLine(in);
		// c.2. get canvas from DB
		canvasDB = canvasService.getCanvasByUserId(userId);
		// c.3. assert equality 
		assertEquals(canvas.getUserId(), canvasDB.getUserId());
		assertEquals(canvas.getWidth(), canvasDB.getWidth());
		assertEquals(canvas.getHeight(), canvasDB.getHeight());
		assertNotNull(canvas.getMatrix());
		assertEquals(canvas.getMatrix().length, canvasDB.getMatrix().length);
		assertEquals(countXInCanvas(canvas), countXInCanvas(canvasDB));

		// d.1. insert down line
		in = new InsertLineInputDTO();
		in.setUserId(userId);
		in.setX1(1);
		in.setY1(height);
		in.setX2(width);
		in.setY2(height);
		canvas = canvasService.insertLine(in);
		// d.2. get canvas from DB
		canvasDB = canvasService.getCanvasByUserId(userId);
		// d.3. assert equality 
		assertEquals(canvas.getUserId(), canvasDB.getUserId());
		assertEquals(canvas.getWidth(), canvasDB.getWidth());
		assertEquals(canvas.getHeight(), canvasDB.getHeight());
		assertNotNull(canvas.getMatrix());
		assertEquals(canvas.getMatrix().length, canvasDB.getMatrix().length);
		assertEquals(countXInCanvas(canvas), countXInCanvas(canvasDB));
		
		List<CanvasDTO> allCanvas = canvasService.getAllCanvas();
		assertNotNull(allCanvas);
		assertTrue(allCanvas.size() > 0);
		assertEquals(1, allCanvas.size());
		
		printCanvasState(canvasDB);
	}

	@Test(expected=CommandNotYetImplementedException.class)
	public void testEX_NotVerticalLine() throws CanvasException {
		InsertLineInputDTO in = new InsertLineInputDTO(userId, 6, 2, 7, 4);
		canvasService.insertLine(in);
	}

	@Test(expected=CommandWrongParamsException.class)
	public void testEX_X1GreaterThanX2() throws CanvasException {
		InsertLineInputDTO in = new InsertLineInputDTO(userId, 4, 2, 1, 2);
		canvasService.insertLine(in);
	}
	
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_X1LessThanZero() throws CanvasException {
		InsertLineInputDTO in = new InsertLineInputDTO(userId, -2, 2, 6, 2);
		canvasService.insertLine(in);
	}
	
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_X1EqualZero() throws CanvasException {
		InsertLineInputDTO in = new InsertLineInputDTO(userId, 0, 2, 6, 2);
		canvasService.insertLine(in);
	}
	
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_X2GreaterThanWidth() throws CanvasException {
		InsertLineInputDTO in = new InsertLineInputDTO(userId, 1, 2, width+1, 2);
		canvasService.insertLine(in);
	}

	@Test
	public void testCL_X2EqualWidth() throws CanvasException {
		InsertLineInputDTO in = new InsertLineInputDTO(userId, 6, 2, width, 2);
		canvasService.insertLine(in);
	}
	
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_YLessThanZero() throws CanvasException {
		InsertLineInputDTO in = new InsertLineInputDTO(userId, 1, -2, 6, -2);
		canvasService.insertLine(in);
	}

	@Test(expected=CommandWrongParamsException.class)
	public void testEX_YEqualZero() throws CanvasException {
		InsertLineInputDTO in = new InsertLineInputDTO(userId, 1, 0, 6, 0);
		canvasService.insertLine(in);
	}
	
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_YGreaterThanHeight() throws CanvasException {
		InsertLineInputDTO in = new InsertLineInputDTO(userId, 1, height+1, 6, height+1);
		canvasService.insertLine(in);
	}
	
	//vertical lines
	
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_Y1GreaterThanY2() throws CanvasException {
		InsertLineInputDTO in = new InsertLineInputDTO(userId, 6, 4, 6, 3);
		canvasService.insertLine(in);
	}
	
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_Y1LessThanZero() throws CanvasException {
		InsertLineInputDTO in = new InsertLineInputDTO(userId, 6, -1, 6, 4);
		canvasService.insertLine(in);
	}

	@Test(expected=CommandWrongParamsException.class)
	public void testEX_Y1EqualZero() throws CanvasException {
		InsertLineInputDTO in = new InsertLineInputDTO(userId, 6, 0, 6, 4);
		canvasService.insertLine(in);
	}
	
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_Y2GreaterThanHeight() throws CanvasException {
		InsertLineInputDTO in = new InsertLineInputDTO(userId, 6, 2, 6, height+1);
		canvasService.insertLine(in);
	}
	
	@Test
	public void testCL_Y2EqualHeight() throws CanvasException {
		InsertLineInputDTO in = new InsertLineInputDTO(userId, 6, 2, 6, height);
		canvasService.insertLine(in);
	}

	@Test(expected=CommandWrongParamsException.class)
	public void testEX_XLessThanZero() throws CanvasException {
		InsertLineInputDTO in = new InsertLineInputDTO(userId, -1, 3, -1, 4);
		canvasService.insertLine(in);
	}
	
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_XEqualZero() throws CanvasException {
		InsertLineInputDTO in = new InsertLineInputDTO(userId, 0, 3, 0, 4);
		canvasService.insertLine(in);
	}
	
	@Test(expected=CommandWrongParamsException.class)
	public void testEX_XGreaterThanWidth() throws CanvasException {
		InsertLineInputDTO in = new InsertLineInputDTO(userId, width+1, 3, width+1, 4);
		canvasService.insertLine(in);
	}
	
	@Test
	public void testCL_XEqualWidth() throws CanvasException {
		InsertLineInputDTO in = new InsertLineInputDTO(userId, width, 3, width, 4);
		canvasService.insertLine(in);
	}
	
}

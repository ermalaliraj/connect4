package com.ea.connect4.be.service;

import static org.junit.Assert.assertEquals;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ea.examples.canvas.exception.CanvasException;
import com.ea.examples.canvas.exception.NoCanvasException;
import com.ea.examples.canvas.util.CanvasCostant;
import com.ea.examples.connect4.api.CanvasDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml" })
public abstract class AbstractSpringTest {

	protected static transient Log logger = LogFactory.getLog(AbstractSpringTest.class);

	protected void printCanvasState(CanvasDTO canvas) throws CanvasException {
		if (canvas != null && canvas.getMatrix() != null) {
			// first row ----------
			for (int j = 0; j < canvas.getWidth() + 2; j++) {
				System.out.print("-");
			}
			System.out.println();

			// the body of the canvas | **** |
			for (int i = 0; i < canvas.getHeight(); i++) {
				for (int j = 0; j < canvas.getWidth() + 2; j++) {
					if (j == 0 || j == canvas.getWidth() + 1) {
						System.out.print("|");
					} else {
						System.out.print(canvas.getMatrix()[i][j - 1]);
					}
				}
				System.out.println();
			}

			// footer --------
			for (int j = 0; j < canvas.getWidth() + 2; j++) {
				System.out.print("-");
			}
			System.out.println("\n");
		} else {
			throw new NoCanvasException("Canvas not yet initialized!");
		}
	}
	
	
	protected int countXInCanvas(CanvasDTO canvas) throws CanvasException {
		return countXInCanvas(canvas, CanvasCostant.PIXEL);
	}
	protected int countXInCanvas(CanvasDTO canvas, String pixelToFind) throws CanvasException {
		String mat[][] = canvas.getMatrix();	
		int countStars = 0;
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat[i].length; j++) {				
				//count all the 'x' present in the matrix.
				if(mat[i][j].equals(pixelToFind)){
					countStars ++;	
				}
			}
		}
		return countStars;
	}
	
	protected void assertExpected(CanvasDTO canvas, int expected) throws CanvasException {
		assertExpected(canvas, CanvasCostant.PIXEL, expected);
	}
	protected void assertExpected(CanvasDTO canvas, String pixelToFind, int expected) throws CanvasException {
		int actual =  countXInCanvas(canvas, pixelToFind);
		assertEquals(actual, expected);
	}
}
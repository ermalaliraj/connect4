package com.ea.examples.connect4.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.examples.canvas.cmd.CommandBucketFill;
import com.ea.examples.canvas.cmd.CommandLine;
import com.ea.examples.canvas.cmd.CommandRectangle;
import com.ea.examples.canvas.core.Canvas;
import com.ea.examples.canvas.exception.CanvasException;
import com.ea.examples.canvas.exception.NoCanvasException;
import com.ea.examples.connect4.api.CanvasDTO;
import com.ea.examples.connect4.api.FillBucketInputDTO;
import com.ea.examples.connect4.api.InsertLineInputDTO;
import com.ea.examples.connect4.api.InsertRectangleInputDTO;
import com.ea.examples.connect4.api.NewCanvasInputDTO;
import com.ea.examples.connect4.dao.CanvasDAO;
import com.ea.examples.connect4.service.CanvasService;
import com.ea.examples.connect4.util.ConnectUtil;

@Service
public class CanvasServiceImpl implements CanvasService {

	protected static transient Log logger = LogFactory.getLog(CanvasServiceImpl.class);

	@Autowired
	private CanvasDAO canvasDAO;

	public CanvasDTO newCanvas(NewCanvasInputDTO in) throws CanvasException {
		Canvas canvas = new Canvas(in.getWidth(), in.getHeight());
		CanvasDTO dto = ConnectUtil.getCanvasStateAsDTO(canvas);
		dto.setUserId(in.getUserId());

		canvasDAO.newCanvas(dto);
		return dto;
	}

	/**
	 * 1. Read the canvas for the specified user 
	 * 2. Insert the new line 
	 * 3. Persist the new state of the canvas 
	 * 4. return the new state of the canvas
	 */
	public CanvasDTO insertLine(InsertLineInputDTO in) throws CanvasException {
		// 1. GET GAME - read last saved canvas for the specific user
		String userId = in.getUserId();
		CanvasDTO savedCanvas = getCanvasByUserId(userId);
		if (savedCanvas == null) {
			throw new NoCanvasException("No Canvas found for user " + userId);
		} else {
			// 2. PLAY - change the state of the canvas
			Canvas canvas = new Canvas(savedCanvas.getWidth(), savedCanvas.getHeight(), savedCanvas.getMatrix());
			CommandLine cmd = new CommandLine(in.getX1(), in.getY1(), in.getX2(), in.getY2(), canvas);
			//canvas.insertLine(cmd);
			cmd.execute();
			CanvasDTO newCanvas = ConnectUtil.getCanvasStateAsDTO(canvas);
			newCanvas.setId(savedCanvas.getId());
			newCanvas.setUserId(savedCanvas.getUserId());

			// 3. SAVE - save the new state of the canvas in DB
			canvasDAO.updateCanvas(newCanvas);
			return newCanvas;
		}
	}

	public CanvasDTO insertRectangle(InsertRectangleInputDTO in) throws CanvasException {
		// 1. GET GAME - read last saved canvas for the specific user
		String userId = in.getUserId();
		CanvasDTO savedCanvas = getCanvasByUserId(userId);
		if (savedCanvas == null) {
			throw new NoCanvasException("No Canvas found for user " + userId);
		} else {
			// 2. PLAY - change the state of the canvas
			Canvas canvas = new Canvas(savedCanvas.getWidth(), savedCanvas.getHeight(), savedCanvas.getMatrix());
			CommandRectangle cmd = new CommandRectangle(in.getX1(), in.getY1(), in.getX2(), in.getY2(), canvas);
			//canvas.insertRectangle(cmd);
			cmd.execute();
			CanvasDTO newCanvas = ConnectUtil.getCanvasStateAsDTO(canvas);
			newCanvas.setId(savedCanvas.getId());
			newCanvas.setUserId(savedCanvas.getUserId());

			// 3. SAVE - save the new state of the canvas in DB
			canvasDAO.updateCanvas(newCanvas);
			return newCanvas;
		}
	}

	public CanvasDTO fillArea(FillBucketInputDTO in) throws CanvasException {
		// 1. GET GAME - read last saved canvas for the specific user
		String userId = in.getUserId();
		CanvasDTO savedCanvas = getCanvasByUserId(userId);
		if (savedCanvas == null) {
			throw new NoCanvasException("No Canvas found for user " + userId);
		} else {
			// 2. PLAY - change the state of the canvas
			Canvas canvas = new Canvas(savedCanvas.getWidth(), savedCanvas.getHeight(), savedCanvas.getMatrix());
			CommandBucketFill cmd = new CommandBucketFill(in.getX(), in.getY(), in.getColor(), canvas);
			//canvas.fillBucket(cmd);
			cmd.execute();
			CanvasDTO newCanvas = ConnectUtil.getCanvasStateAsDTO(canvas);
			newCanvas.setId(savedCanvas.getId());
			newCanvas.setUserId(savedCanvas.getUserId());

			// 3. SAVE - save the new state of the canvas in DB
			canvasDAO.updateCanvas(newCanvas);
			return newCanvas;
		}
	}

	public void deleteCanvasByUserId(String userId) throws CanvasException {
		canvasDAO.deleteCanvasByUserId(userId);
	}

	public CanvasDTO getCanvasById(Integer id) throws CanvasException {
		return canvasDAO.getCanvasById(id);
	}

	public CanvasDTO getCanvasByUserId(String userId) throws CanvasException {
		return canvasDAO.getCanvasByUserId(userId);
	}

	public List<CanvasDTO> getAllCanvas() throws CanvasException {
		return canvasDAO.getAllCanvas();
	}

}

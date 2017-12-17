package com.ea.connect4.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ea.connect4.api.CanvasDTO;
import com.ea.connect4.api.FillBucketInputDTO;
import com.ea.connect4.api.InsertLineInputDTO;
import com.ea.connect4.api.InsertRectangleInputDTO;
import com.ea.connect4.api.NewCanvasInputDTO;
import com.ea.connect4.canvas.cmd.CommandBucketFill;
import com.ea.connect4.canvas.cmd.CommandLine;
import com.ea.connect4.canvas.cmd.CommandRectangle;
import com.ea.connect4.canvas.core.Canvas;
import com.ea.connect4.canvas.core.CanvasImpl;
import com.ea.connect4.canvas.exception.CanvasException;
import com.ea.connect4.canvas.exception.NoCanvasException;
import com.ea.connect4.dao.CanvasDAO;
import com.ea.connect4.service.CanvasService;

@Service
public class CanvasServiceImpl implements CanvasService {
	
	protected static transient Log logger = LogFactory.getLog(CanvasServiceImpl.class);
	
	@Autowired
	private CanvasDAO canvasDAO;

	public CanvasDTO newCanvas(NewCanvasInputDTO in) throws CanvasException {
		Canvas canvas = new CanvasImpl(in.getWidth(), in.getHeight());
		CanvasDTO dto = canvas.getCanvasStateAsDTO();
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
		if(savedCanvas == null){
			throw new NoCanvasException("No Canvas found for user "+userId);
		}else{
			//2. PLAY - change the state of the canvas
			Canvas canvas = new CanvasImpl(savedCanvas);
			CommandLine cmd = new CommandLine(in.getX1(), in.getY1(), in.getX2(), in.getY2());
			canvas.insertLine(cmd);
			CanvasDTO newCanvas = canvas.getCanvasStateAsDTO();
			newCanvas.setId(savedCanvas.getId());
			newCanvas.setUserId(savedCanvas.getUserId());
			
			//3. SAVE - save the new state of the canvas in DB
			canvasDAO.updateCanvas(newCanvas);
			return newCanvas;
		}
	}
	
	public CanvasDTO insertRectangle(InsertRectangleInputDTO in) throws CanvasException {
		// 1. GET GAME - read last saved canvas for the specific user
		String userId = in.getUserId();
		CanvasDTO savedCanvas = getCanvasByUserId(userId);
		if(savedCanvas == null){
			throw new NoCanvasException("No Canvas found for user "+userId);
		}else{
			//2. PLAY - change the state of the canvas
			Canvas canvas = new CanvasImpl(savedCanvas);
			CommandRectangle cmd = new CommandRectangle(in.getX1(), in.getY1(), in.getX2(), in.getY2());
			canvas.insertRectangle(cmd);
			CanvasDTO newCanvas = canvas.getCanvasStateAsDTO();
			newCanvas.setId(savedCanvas.getId());
			newCanvas.setUserId(savedCanvas.getUserId());
			
			//3. SAVE - save the new state of the canvas in DB
			canvasDAO.updateCanvas(newCanvas);
			return newCanvas;
		}
	}

	public CanvasDTO fillArea(FillBucketInputDTO in) throws CanvasException {
		// 1. GET GAME - read last saved canvas for the specific user
		String userId = in.getUserId();
		CanvasDTO savedCanvas = getCanvasByUserId(userId);
		if(savedCanvas == null){
			throw new NoCanvasException("No Canvas found for user "+userId);
		}else{
			//2. PLAY - change the state of the canvas
			Canvas canvas = new CanvasImpl(savedCanvas);
			CommandBucketFill cmd = new CommandBucketFill(in.getX(), in.getY(), in.getColor());
			canvas.fillBucket(cmd);
			CanvasDTO newCanvas = canvas.getCanvasStateAsDTO();
			newCanvas.setId(savedCanvas.getId());
			newCanvas.setUserId(savedCanvas.getUserId());
			
			//3. SAVE - save the new state of the canvas in DB
			canvasDAO.updateCanvas(newCanvas);
			return newCanvas;
		}
	}

	public void deleteCanvasByUserId(String userId) throws CanvasException{
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

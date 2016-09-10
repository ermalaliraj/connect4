package com.ea.connect4.dao;

import java.util.List;

import com.ea.connect4.api.CanvasDTO;
import com.ea.connect4.canvas.exception.CanvasException;

public interface CanvasDAO {

	void newCanvas(CanvasDTO e) throws CanvasException;
	
	void updateCanvas(CanvasDTO e) throws CanvasException;
	
	void deleteCanvasByUserId(String userId) throws CanvasException;
	
	CanvasDTO getCanvasById(Integer canvasId) throws CanvasException;

	CanvasDTO getCanvasByUserId(String userId) throws CanvasException;
	
	List<CanvasDTO> getAllCanvas() throws CanvasException;

}

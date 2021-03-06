package com.ea.connect4.service;

import java.util.List;

import com.ea.connect4.api.CanvasDTO;
import com.ea.connect4.api.FillBucketInputDTO;
import com.ea.connect4.api.InsertLineInputDTO;
import com.ea.connect4.api.InsertRectangleInputDTO;
import com.ea.connect4.api.NewCanvasInputDTO;
import com.ea.connect4.canvas.exception.CanvasException;

public interface CanvasService {

	CanvasDTO newCanvas(NewCanvasInputDTO in) throws CanvasException;
	
	CanvasDTO insertLine(InsertLineInputDTO in) throws CanvasException;
	
	CanvasDTO insertRectangle(InsertRectangleInputDTO in) throws CanvasException;

	CanvasDTO fillArea(FillBucketInputDTO cmd) throws CanvasException;
	
	void deleteCanvasByUserId(String userId) throws CanvasException;
	
	CanvasDTO getCanvasById(Integer userId) throws CanvasException;

	CanvasDTO getCanvasByUserId(String userId) throws CanvasException;

	List<CanvasDTO> getAllCanvas() throws CanvasException;
	
}

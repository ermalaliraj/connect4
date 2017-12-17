package com.ea.examples.connect4.util;

import com.ea.examples.canvas.core.Canvas;
import com.ea.examples.connect4.api.CanvasDTO;

public class ConnectUtil {

	public static CanvasDTO getCanvasStateAsDTO(Canvas canvas) {
		CanvasDTO dto = new CanvasDTO();
		dto.setWidth(canvas.getWidth());
		dto.setHeight(canvas.getHeight());
		dto.setMatrix(canvas.getMatrix());
		return dto;
	}

}

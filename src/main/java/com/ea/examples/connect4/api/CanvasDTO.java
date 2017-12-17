package com.ea.examples.connect4.api;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CanvasDTO implements Serializable {

	private static final long serialVersionUID = -2193229209209688840L;
	
	private String userId;
	private Integer id;
	private Integer width;
	private Integer height;
	private String[][] matrix;
	
	public CanvasDTO() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(String[][] matrix) {
		this.matrix = matrix;
	}
	
	public String toString() {
        StringBuilder sb =  new StringBuilder("CanvasDTO[");
		sb.append("id=" + id + ", ");
		sb.append("userId=" + userId + ", ");
		sb.append("width=" + width + ", ");
		sb.append("height=" + height + ", ");
		
		String m = null;
		if(matrix != null){
			m = "";
			// header -------
			for (int j = 0; j < width + 2; j++) {
				m += "-";
			}
			m += "\n";
			// the body of the canvas | **** |
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < width + 2; j++) {
					if (j == 0 || j == width + 1) {
						m += "|";
					} else {
						m += matrix[i][j-1];
					}
				}
				m += "\n";
			}
			// footer --------
			for (int j = 0; j < width + 2; j++) {
				m += "-";
			}
			m += "\n";
		}
		
        sb.append("matrix=\n" + m);
        sb.append("] ");
        
        return sb.toString();
    }
	
}

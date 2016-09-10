package com.ea.connect4.api;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class NewCanvasInputDTO implements Serializable {

	private static final long serialVersionUID = -2193229209209688840L;
	
	private String userId;
	private int width;
	private int height;
	
	public NewCanvasInputDTO(){
		
	}
	public NewCanvasInputDTO(String userId, int width, int height) {
		this.userId = userId;
		this.width = width;
		this.height = height;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String toString() {
        return new ToStringBuilder(this.getClass().getSimpleName(), ToStringStyle.SHORT_PREFIX_STYLE)
        	//.appendSuper(super.toString())
        	.append("userId", userId)
        	.append("width", width)
        	.append("height", height)
        	.toString();
    }
	
}

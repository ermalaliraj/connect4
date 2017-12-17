package com.ea.examples.connect4.api;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class FillBucketInputDTO {

	private String userId;
	private int x;
	private int y;
	private String color;
	
	public FillBucketInputDTO(){}
	
	public FillBucketInputDTO(String userId, int x, int y, String color){
		this.userId = userId;
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}

	public String toString() {
        return new ToStringBuilder(this.getClass().getSimpleName(), ToStringStyle.SHORT_PREFIX_STYLE)
        	.append("userId", userId)	
        	.append("x", x)
        	.append("y", y)
        	.append("color", color)
        	.toString();
    }
}

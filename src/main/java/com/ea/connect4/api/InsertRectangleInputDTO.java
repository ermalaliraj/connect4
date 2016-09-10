package com.ea.connect4.api;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class InsertRectangleInputDTO {

	private String userId;
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	public InsertRectangleInputDTO(){}
	
	public InsertRectangleInputDTO(String userId, int x1, int y1, int x2, int y2){
		this.userId = userId;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;	
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getX1() {
		return x1;
	}
	public void setX1(int x1) {
		this.x1 = x1;
	}
	public int getY1() {
		return y1;
	}
	public void setY1(int y1) {
		this.y1 = y1;
	}
	public int getX2() {
		return x2;
	}
	public void setX2(int x2) {
		this.x2 = x2;
	}
	public int getY2() {
		return y2;
	}
	public void setY2(int y2) {
		this.y2 = y2;
	}
	
	public String toString() {
        return new ToStringBuilder(this.getClass().getSimpleName(), ToStringStyle.SHORT_PREFIX_STYLE)
        	//.appendSuper(super.toString())
        	.append("x1", x1)
        	.append("y1", y1)
        	.append("x2", x2)
        	.append("y2", y2)
        	.toString();
    }
}

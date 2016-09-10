package com.ea.connect4.api;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Canvas {

	private String userId;
	private Integer id;
	private int width;
	private int height;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
}

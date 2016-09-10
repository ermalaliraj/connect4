package com.ea.connect4.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
public class CanvasEntity  {

	@Id
	@GeneratedValue
	private Integer id;
	
	@Column(unique=true)
	private String userId; //use different field if in future we need a relation between id and userId. If one or more users can work on the same Canvas
	private int width;
	private int height;
	
	@Column( length = 10000 )
	private String[][] matrix;

	public CanvasEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer  id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(String[][] matrix) {
		this.matrix = matrix;
	}

	public boolean equals(final Object other) {
        if (!(other instanceof CanvasEntity))
            return false;
        CanvasEntity o = (CanvasEntity) other;
      
        return new EqualsBuilder()
        		.append(id, o.id)
        		.isEquals();
    }
    public int hashCode() {
        return new HashCodeBuilder()
        		.append(id)
        		.toHashCode();
    }
	public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
        	.appendSuper(super.toString())
        	.append("id", id)
        	.append("width", width)
        	.append("height", height)
        	.append("matrix", matrix)
        	.toString();
    }

}

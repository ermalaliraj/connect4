package com.ea.examples.connect4.ws.rest;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.ea.examples.canvas.exception.CanvasException;
import com.ea.examples.canvas.exception.NoCanvasException;
import com.ea.examples.connect4.api.CanvasDTO;
import com.ea.examples.connect4.api.FillBucketInputDTO;
import com.ea.examples.connect4.api.InsertLineInputDTO;
import com.ea.examples.connect4.api.InsertRectangleInputDTO;
import com.ea.examples.connect4.api.NewCanvasInputDTO;
import com.ea.examples.connect4.service.CanvasService;
import com.ea.examples.connect4.ws.exception.RESTFullException;

@Component
@Path("/connect4")
public class RESTFulResource {

	protected static transient Log logger = LogFactory.getLog(RESTFulResource.class);

	@Autowired
	CanvasService canvasService;
	
	@PostConstruct
	public void init() {
		//if not, spring objects will not be autowired from servlet context
	    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@GET 
	@Path("/ping")
	@Produces(MediaType.TEXT_HTML)
	public String ping() {
		return "Date: "+new Date();
	}

	@GET
    @Path("/new/{userId}/{width}/{height}")
    @Produces(MediaType.APPLICATION_XML)
    public CanvasDTO newCanvas( @PathParam("userId") String userId
								,@PathParam("width") int width
								,@PathParam("height") int height) {
		try {
			logger.debug("Request for creating new Canvas  width:"+width+", height:"+height+" for user:"+userId);
			NewCanvasInputDTO in = new NewCanvasInputDTO(userId, width, height);
			CanvasDTO canvas = canvasService.newCanvas(in);
			return canvas;
		} catch (CanvasException e) {
			logger.debug("ErrorCanvas: ", e);
			throw new RESTFullException("A Canvas error occurred while trying to insert new canvas. "+e.getMessage());
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new RESTFullException("System error occured while trying to insert new canvas. "+e.getMessage());
		}
    }
	
	@GET
    @Path("/line/{userId}/{x1}/{y1}/{x2}/{y2}")
    @Produces(MediaType.APPLICATION_XML)
    public CanvasDTO insertLine( @PathParam("userId") String userId
					    		,@PathParam("x1") int x1
								,@PathParam("y1") int y1
								,@PathParam("x2") int x2
								,@PathParam("y2") int y2) {
		try {
			logger.debug("Request for inserting new line ("+x1+", "+y1+"), ("+x2+", "+y2+") for  user:"+userId);
			InsertLineInputDTO in = new InsertLineInputDTO(userId, x1, y1, x2, y2);
			CanvasDTO canvas = canvasService.insertLine(in);
			return canvas;
		} catch (NoCanvasException e) {
			logger.debug("NoCanvasException: ", e);
			throw new RESTFullException("No canvas found for user "+userId+". Please create a new canvas first!");
		} 
		catch (CanvasException e) {
			logger.debug("ErrorCanvas: ", e);
			throw new RESTFullException("A Canvas error occurred while trying to insert the new line. "+e.getMessage());
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new RESTFullException("System error occured while trying to insert the new line. "+e.getMessage());
		}
    }
	
	@GET
    @Path("/rectangle/{userId}/{x1}/{y1}/{x2}/{y2}")
    @Produces(MediaType.APPLICATION_XML)
    public CanvasDTO insertRectangle( @PathParam("userId") String userId
					    		,@PathParam("x1") int x1
								,@PathParam("y1") int y1
								,@PathParam("x2") int x2
								,@PathParam("y2") int y2) {
		try {
			logger.debug("Request for inserting new rectangle ("+x1+", "+y1+"), ("+x2+", "+y2+") for  user:"+userId);
			InsertRectangleInputDTO in = new InsertRectangleInputDTO(userId, x1, y1, x2, y2);
			CanvasDTO canvas = canvasService.insertRectangle(in);
			return canvas;
		} catch (NoCanvasException e) {
			logger.debug("NoCanvasException: ", e);
			throw new RESTFullException("No canvas found for user "+userId+". Please create a new canvas first!");
		} catch (CanvasException e) {
			logger.debug("ErrorCanvas: ", e);
			throw new RESTFullException("A Canvas error occurred while trying to insert the new rectangle. "+e.getMessage());
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new RESTFullException("System error occured while trying to insert the new rectangle. "+e.getMessage());
		}
    }
	
	@GET
    @Path("/fill/{userId}/{x}/{y}/{color}")
    @Produces(MediaType.APPLICATION_XML)
    public CanvasDTO fillArea( @PathParam("userId") String userId
					    		,@PathParam("x") int x
								,@PathParam("y") int y
								,@PathParam("color") String color) {
		try {
			logger.debug("Request for filling area around pixel ("+x+", "+y+") for  user:"+userId);
			FillBucketInputDTO in = new FillBucketInputDTO(userId, x, y, color);
			CanvasDTO canvas = canvasService.fillArea(in);
			return canvas;
		} catch (NoCanvasException e) {
			logger.debug("NoCanvasException: ", e);
			throw new RESTFullException("No canvas found for user "+userId+". Please create a new canvas first!");
		} 
		catch (CanvasException e) {
			logger.debug("ErrorCanvas: ", e);
			throw new RESTFullException("A Canvas error occurred while trying to fill a bucket. "+e.getMessage());
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new RESTFullException("System error occured while trying to fill a bucket. "+e.getMessage());
		}
    }
	
	@GET
    @Path("/getCanvas/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public CanvasDTO getCanvas(@PathParam("id") Integer id) {
		try {
			CanvasDTO canvas = canvasService.getCanvasById(id);
			return canvas;
		} catch (CanvasException e) {
			logger.debug("ErrorCanvas: ", e);
			throw new RESTFullException("A Canvas error occurred while trying to get canvas: "+id+", Error:"+e.getMessage());
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new RESTFullException("System error occured while trying to get canvas:"+id+", Error:"+e.getMessage());
		}
    }
	
	@GET
    @Path("/getCanvasByUser/{userId}")
    @Produces(MediaType.APPLICATION_XML)
    public CanvasDTO getCanvasByUser(@PathParam("userId") String userId) {
		try {
			CanvasDTO canvas = canvasService.getCanvasByUserId(userId);
			return canvas;
		} catch (CanvasException e) {
			logger.debug("ErrorCanvas: ", e);
			throw new RESTFullException("A Canvas error occurred while trying to get Canvas for user "+userId+", Error:"+e.getMessage());
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new RESTFullException("System error occured while trying to get Canvas for user "+userId+", Error:"+e.getMessage());
		}
    }
	
	@GET
    @Path("/getAllCanvas")
    @Produces(MediaType.APPLICATION_XML)
    public List<CanvasDTO> getAllCanvas() {
		try {
			List<CanvasDTO> list = canvasService.getAllCanvas();
			return list;
		} catch (CanvasException e) {
			logger.debug("ErrorCanvas: ", e);
			throw new RESTFullException("A Canvas error occurred while trying to get all canvas. "+e.getMessage());
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new RESTFullException("System error occured while trying to get all canvas. "+e.getMessage());
		}
    }
	
	@GET
    @Path("/deleteCanvasByUser/{userId}")
    @Produces(MediaType.TEXT_HTML)
    public String deleteCanvasByUser(@PathParam("userId") String userId) {
		try {
			canvasService.deleteCanvasByUserId(userId);
			return "OK";
		} catch (CanvasException e) {
			logger.debug("ErrorCanvas: ", e);
			throw new RESTFullException("A Canvas error occurred while trying to delete canvas for user "+userId+", Error:"+e.getMessage());
		} catch (Exception e) {
			logger.debug("Exception: ", e);
			throw new RESTFullException("System error occured while trying to delete canvas for user "+userId+", Error:"+e.getMessage());
		}
    }
	
}
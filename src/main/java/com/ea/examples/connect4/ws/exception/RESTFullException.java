package com.ea.examples.connect4.ws.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * For the moment reply to FE only the message. 
 * Must be extended to output even the stanck trace if needed.
 * 
 * @author Ermal Aliraj
 *
 */
public class RESTFullException extends WebApplicationException {
	
	private static final long serialVersionUID = -9131891839863143739L;

	public RESTFullException(String message) {
		super(Response.status(Response.Status.UNAUTHORIZED)
				.entity(message).type(MediaType.TEXT_PLAIN).build());
	}
}
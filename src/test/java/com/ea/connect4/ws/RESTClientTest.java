package com.ea.connect4.ws;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.ws.rs.core.MediaType;

import com.ea.examples.connect4.api.CanvasDTO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

public class RESTClientTest {

	/**
	 * Test in sequence all application functionalities
	 */
	public static void main(String[] args) {
		String url = "";
		url = "http://localhost:1009/connect4/new/0001/6/4";
		call(url, MediaType.APPLICATION_XML);
		url = "http://localhost:1009/connect4/line/0001/1/1/5/1";
		call(url, MediaType.APPLICATION_XML);
		url = "http://localhost:1009/connect4/rectangle/0001/2/3/5/4";
		call(url, MediaType.APPLICATION_XML);
		url = "http://localhost:1009/connect4/fill/0001/3/2/o";
		call(url, MediaType.APPLICATION_XML);
		url = "http://localhost:1009/connect4/getCanvas/1";
		call(url, MediaType.APPLICATION_XML);
		url = "http://localhost:1009/connect4/getAllCanvas";
		callForList(url);
		url = "http://localhost:1009/connect4/getCanvasByUser/0001";
		call(url, MediaType.APPLICATION_XML);
		url = "http://localhost:1009/connect4/deleteCanvasByUser/0001";
		call(url, MediaType.TEXT_HTML);
	}

	private static void call(String url, String type) {
		try{
			WebResource webResource = Client.create().resource(url);
			ClientResponse response = webResource.accept(type).get(ClientResponse.class);
			
			if (200 != response.getStatus()) {
				System.out.println("Fail to process response : " + response.getStatus());
			} else {
				System.out.println("Server correctly responded. Chech the response.");
			}
			
			if(type.equals(MediaType.TEXT_HTML)){
				System.out.println("Response from server: " + response);	
			} else {
				CanvasDTO canvas = response.getEntity(CanvasDTO.class);
				if(canvas==null || canvas.getId()==null){
					System.out.println("Response from server: No canvas!");	
				} else {
					System.out.println("Response from server: " + canvas);
				}
			} 
			
			//let the time to the server to reply before doing next call
			try {Thread.sleep(500);}
			catch (InterruptedException e) {}
		}
		catch(Exception e){
			System.out.println("Error calling: "+url);
			e.printStackTrace();
		}
	}
	
	private static void callForList(String url) {
		WebResource webResource = Client.create().resource(url);
		GenericType<List<CanvasDTO>> genericType = new GenericType<List<CanvasDTO>>() {};
		Builder builder = webResource.accept("application/xml");
		List<CanvasDTO> response = builder.get(genericType);
		
		System.out.println("Response from server: " + response); 
	}

	/**
	 * Not used for simplicity, testable from browser, we made all methods @GET
	 */
	@SuppressWarnings("unused")
	private void put(String urlString) throws Exception{
		URL url = new URL(urlString);
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("PUT");
		OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
		out.write("Resource content");
		out.close();
		httpCon.getInputStream();
	}
	
	/**
	 * Not used for simplicity, testable from browser, we made all methods @GET
	 */
	@SuppressWarnings("unused")
	private void delete(String urlString) throws Exception{
		URL url = new URL(urlString);
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestProperty(
		    "Content-Type", "application/x-www-form-urlencoded" );
		httpCon.setRequestMethod("DELETE");
		httpCon.connect();
	}

}
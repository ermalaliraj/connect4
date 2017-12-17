package com.ea.examples.connect4.ws.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;

import com.sun.jersey.spi.container.servlet.ServletContainer;

public class JettyServer {
	
	public final static int port = 1009; 
	
	public static void main(String[] args) throws Exception {
		ServletHolder sh = new ServletHolder(ServletContainer.class);
		sh.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
		sh.setInitParameter("com.sun.jersey.config.property.packages", "com.ea.examples.connect4.ws.rest");
		sh.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");

		final ServletContextHandler context = new ServletContextHandler ();          
        context.setContextPath( "/" ); 
        context.addEventListener( new ContextLoaderListener() ); 
        context.setInitParameter( "contextConfigLocation", "classpath*:**/spring-context.xml");
        context.addServlet( sh, "/*" );   
        
        final Server server = new Server(port);
        server.setHandler( context ); 
        server.start(); 
        server.join(); 
	}
}
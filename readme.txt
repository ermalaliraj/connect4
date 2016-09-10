Index
1.	Specific
2.	Application overview
4.	Source packages
5.	Test packages
5.	Test the application
6.	Timing


1) The challenge

Develop a simple "Connect-4" game web service running either as a WAR in jetty or
as an embedded-jetty application, which is able to start a game session, take player
input, keep the state of the game and forward the correct response to the requesting
party.

Canvas Game
The program should support the following commands:
C w h Should create a new canvas of width w and height h.
L x1 y1 x2 y2 Should create a new line from (x1,y1) to (x2,y2). Currently only horizontal or vertical lines are supported. Horizontal and vertical lines will be drawn using the 'x' character.
R x1 y1 x2 y2 Should create a new rectangle, whose upper left corner is (x1,y1) and lower right corner is (x2,y2). Horizontal and vertical lines will be drawn using the 'x' character.
B x y c Should fill the entire area connected to (x,y) with "colour" c. The behaviour of this is the same as that of the "bucket fill" tool in paint programs.


2) Application overview

From a high level we can see the application subdivided in 4 "components":

1. The Game Implementation - Classes inside canvas package. Almost didn't touch the actual implementation of the game. So we need to convert InputDTOs to CommandCanvas for calling the Canvas.
2. Business Logic Layer
	2.a. Data Access. H2 database, EntityManager and the DAO package for accessing the data to the DB. In this layer we convert Enity objects to DTOs so the application in the higher layers can speak only "DTO language".
	2.b. Service layer. The layer which first handles game logic (using canvas package) then handles 
	the persistence of data in the DB (using dao package). This is the transactional layer. Transactions are configured in the file spring-context.xml using AOP.
3. WebService / REST Layer. The classes responsible for exposing the code as  RESTFul WebService. We decided to reply XML message.
4. Api of the application. Requests/Responses/DTOs to reply to the caller.


3) Source Packages

The application is composed by the following packages:

com.ea.connect4.api  - API of the application. Here we have all the DTOs to communicate with the caller.      
com.ea.connect4.canvas.cmd  - Commands of the game
com.ea.connect4.canvas.core - Implementation fo the game
com.ea.connect4.canvas.exception - Exceptions of the game
com.ea.connect4.canvas.util      - Utilities used for the game
com.ea.connect4.dao      - Dao interface
com.ea.connect4.dao.impl - DAO implementation. If we change DB, this is the only class we have to change.
com.ea.connect4.entity   - Entities to persist in DB
com.ea.connect4.service  - Service interface
com.ea.connect4.service.impl - Service implementation
com.ea.connect4.ws - Jetty Server
com.ea.connect4.ws.rest - Requests handler classes
	

Enhancements
1.Exception 
	1.a. Enrich Connect4 Application exceptions and identify the exceptions that application must rollback when occour. In service layer actually all the operations go in commit since CanvasExcpetion extends RuntimeException and is not set to rollback.
	1.b. Enrich RESTFullException in order to reply to the caller all stacktrace
2.CurrentSession 
	2.a. Actually current session is simulated calling the application with the same user (can be session id or anything to identify a session) In a real application I would use spring-security and authentication for a user to log-in and then use the application.


4) Test packages

Used junit for testing Canvas Service. "TDD" approach was followed for the implementation, 
so unit tests were not created at the end but during all the development phase.
For each functionality of the service is present a different file in the package com.ea.connect4.be.service:
- CommandBucketFillTest.java -> Test cases for BucketFiller command.
- CommandLineTest.java -> Test cases for drawing a line command.
- CommandRectangleTest.java -> Test cases for drawing a rectangle command.

Unit tests are divided in three categories:
- Happy Paths, testHP_nomeTest wich are the tests that have to succeed. Usually we assert at the end of the method what we are expecting.
- Case Limit, testCL_nomeTest wich are the tests that test a Limit Case. Example, x, y are 0 or same as width/height.  Usually we assert what we are expecting or pay attention of eventual exceptions.
- Exceptions  - testEX_nomeTest wich are the tests that test exception cases. In this case we define the exception we are waiting to be thrown.

Inside soapui folder there is a soapui project with the rest calls to test the application.
	
	
5) Test the application

Run the class JettyServer.java which creates a server listening in the port 1009.
The application is ready to be tested using the following requests (for each functionality):
	http://localhost:1009/connect4/new/0001/6/4
	http://localhost:1009/connect4/line/0001/1/1/5/1
	http://localhost:1009/connect4/rectangle/0001/2/3/5/4
	http://localhost:1009/connect4/fill/0001/3/2/o
	http://localhost:1009/connect4/getCanvas/1
	http://localhost:1009/connect4/getAllCanvas
	http://localhost:1009/connect4/getCanvasByUser/0001
	http://localhost:1009/connect4/deleteCanvasByUser/0001
	http://localhost:1009/connect4/ping 

If you want to test all the calls with a single click use the class RESTClientTest.java which calls programatically the server.

For test scope, all restful methods have changed to @GET, so in this case the functionalities are testable even from the browser.
In a real application the insertion methods(new, line, rectangle, fill) would be @PUT (or @POST in case of form submission) and the deleteCanvasByUser will be @DELETE.
	
6) Timing

Total time spend for the release 20 hours subdivided as follows:
	8 hours springcontext/h2/dao/service/junit  (3+3+2)
	8 hours JettyServer Spring/RESTFull + test/soapui (2+4+2)
	4 hours Overview/documentation/repository
	
	
	
	
Bibliografy
	https://www.eclipse.org/jetty/documentation/9.3.x/embedded-examples.html
	https://www.acando.no/thedailypassion/200555/a-rest-service-with-jetty-and-jersey
	http://jlunaquiroga.blogspot.it/2014/01/restful-web-services-with-jetty-and.html
	http://www.robinhowlett.com/blog/2013/02/13/spring-app-migration-from-xml-to-java-based-config/

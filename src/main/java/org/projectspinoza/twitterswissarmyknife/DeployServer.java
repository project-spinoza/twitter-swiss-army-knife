package org.projectspinoza.twitterswissarmyknife;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DeployServer extends AbstractVerticle {

  HttpServer server;
  Router router;
  String host;
  int port;


  public DeployServer() throws IOException {

    this.host = "localhost";
    this.port = 8383;
  }

  /**
   * Deploying the verical
   */
  @Override
  public void start() {
    server = vertx.createHttpServer();
    router = Router.router(vertx);
    // Enable multipart form data parsing
    router.route().handler(BodyHandler.create());
    router.route().handler(CorsHandler.create("*").allowedMethod(HttpMethod.GET)
            .allowedMethod(HttpMethod.POST).allowedMethod(HttpMethod.OPTIONS)
            .allowedHeader("Content-Type, Authorization"));
    // registering different route handlers
    this.registerHandlers();
    server.requestHandler(router::accept).listen(port, host);
  }

  /**
   * For Registering different Routes
   */
  public void registerHandlers() {
    router.route(HttpMethod.GET,"/").blockingHandler(this::welcomeRoute);
    router.route(HttpMethod.POST,"/readCsv").blockingHandler(this::readCsv);
    router.route(HttpMethod.POST,"/createCsv").blockingHandler(this::createCsv);
   

  }

  /**
   * Welcome route
   * 
   * @param routingContext
   */
  public void welcomeRoute(RoutingContext routingContext) {
    routingContext.response().end("<h1> Welcome To TSAK </h1>");
  }

  /**
   * search for the tweets
   * 
   * @param routingContext
   */
  public void readCsv(RoutingContext routingContext) {
    this. enableCors(routingContext.response());
    String response;
    try {
    String Path =((routingContext.request().getParam("Path") == null) ? "F:/GraphWorkSpace/twitter-swiss-army-knife" : routingContext.request().getParam("Path"));
   String  filenames = (Path + "/*.csv");
    response = new ObjectMapper().writeValueAsString("it will read csv file later");
    }catch (Exception ex) {
    response = "{status: 'error', 'msg' : " + ex.getMessage() + "}";
    ex.printStackTrace();
    }
    routingContext.response().end(response);

  }
  
  public void createCsv(RoutingContext routingContext) {
	    this. enableCors(routingContext.response());
	    String response;
	    try {
	    	//routingContext.request().getParam("search_term").toString();
	    String FileName =((routingContext.request().getParam("FileName") == null) ? "output.csv" : routingContext.request().getParam("FileName"));
	 
	  writeFile(FileName, " it will be added later ");
	    response = new ObjectMapper().writeValueAsString("Created Csv File in the root Directory");
	    }catch (Exception ex) {
	    response = "{status: 'error', 'msg' : " + ex.getMessage() + "}";
	    ex.printStackTrace();
	    }
	    routingContext.response().end(response);

	  }
//FUNCTION TO WRITE DATA
	public static void writeFile(String fileName, String data)
			throws IOException {
		FileWriter fileWriter = new FileWriter(new File(fileName+".csv"));
		fileWriter.write(data);
		fileWriter.flush();
		fileWriter.close();
	}
  public void enableCors(HttpServerResponse response) {
      response.putHeader("content-type", "text/plain");
      response.putHeader("Access-Control-Allow-Origin", "*");
      response.putHeader("Access-Control-Allow-Methods",
              "GET, POST, OPTIONS");
      response.putHeader("Access-Control-Allow-Headers",
              "Content-Type, Authorization");
  }
}

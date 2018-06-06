package io.vertx.examples.spring.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServer;

/**
 * Simple web server verticle to expose the results of the Spring service bean call (routed via a verticle - see
 * SpringDemoVerticle)
 */
public class ServerVerticle extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    super.start();
    HttpServer server = vertx.createHttpServer();
    server.requestHandler(req -> {
      if (req.method() == HttpMethod.GET) {
        req.response().setChunked(true);

        if (req.path().equals("/products/insert")) {
          vertx.eventBus().<String>send(SpringDemoVerticle.INSERT_PRODUCTS_ADDRESS, "", result -> {
              if (result.succeeded()) {
                req.response().setStatusCode(200).write(result.result().body()).end();
              } else {
                req.response().setStatusCode(500).write(result.cause().toString()).end();
              }
            });
         
        
            
        }else if(req.path().equals("/products/delete")){
        	 vertx.eventBus().<String>send(SpringDemoVerticle.DELETE_PRODUCTS_ADDRESS, "", result -> {
               if (result.succeeded()) {
                 req.response().setStatusCode(200).write(result.result().body()).end();
               } else {
                 req.response().setStatusCode(500).write(result.cause().toString()).end();
               }
             });
        }else if (req.path().equals("/products/allProducts")){
        	  vertx.eventBus().<String>send(SpringDemoVerticle.ALL_PRODUCTS_ADDRESS, "", result -> {
                  if (result.succeeded()) {
                    req.response().setStatusCode(200).write(result.result().body()).end();
                  } else {
                    req.response().setStatusCode(500).write(result.cause().toString()).end();
                  }
                });
        }else if (req.path().equals("/products/update")){
      	  vertx.eventBus().<String>send(SpringDemoVerticle.UPDATE_PRODUCTS_ADDRESS, "", result -> {
              if (result.succeeded()) {
                req.response().setStatusCode(200).write("Updated: \n "+result.result().body()).end();
              } else {
                req.response().setStatusCode(500).write(result.cause().toString()).end();
              }
            });
    }
        
        else {
          req.response().setStatusCode(200).write("Hello from vert.x").end();
        }

      } else {
        // We only support GET for now
        req.response().setStatusCode(405).end();
      }
    });

    server.listen(8080);
  }
}

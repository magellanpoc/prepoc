package io.vertx.examples.spring.aspect;

import io.vertx.examples.spring.service.ProductService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy()
public class AppConfig {
	@Bean
	  public LoggingAspect loggingAspect(){
		 return new LoggingAspect();
	  }
	  @Bean	
	  public ProductService productService(){
		 return new ProductService();
	  }

}

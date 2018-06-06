package io.vertx.examples.spring.service;

import io.vertx.examples.spring.entity.Product;
import io.vertx.examples.spring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Simple Spring service bean to expose the results of a trivial database call
 */
@Service
public class ProductService {

  @Autowired
  private ProductRepository repo;

  public List<Product> getAllProducts() {
    return repo.findAll();
  }

  public Product insertProduct(Product product) { 
System.out.println("insertProductsHandler1");
	     repo.save(product);
	     return product;
	  }
  
  public String  deleteProduct(Integer id) { 
	     repo.delete(id);
	     return "deleted" + id;
	  }
  
  public Product  getOneProduct(int id) { 
	  	 return repo.findOne(id);
	     
	  }
  
}


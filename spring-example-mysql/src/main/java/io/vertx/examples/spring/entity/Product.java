package io.vertx.examples.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Trivial JPA entity for vertx-spring demo
 */
@Entity
@Table(name="PRODUCT")
public class Product {

  @Id
  @Column(name="ID")
  private Integer productId;

  @Column
  private String description;

  public Integer getProductId() {
    return productId;
  }

  public String getDescription() {
    return description;
  }

public void setProductId(Integer productId) {
	this.productId = productId;
}

public void setDescription(String description) {
	this.description = description;
}
  
  public Product(String description,Integer productId){
	  this.setDescription(description);
	  this.setProductId(productId);
  }
  
  public Product(){
  }
}

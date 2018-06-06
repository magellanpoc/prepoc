package io.rest.springboot.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Hello world!
 *
 */

@Entity
@Table(name="configuration")
public class Configuration 
{
	@Column(name="id")
	@Id
	private int id;
	@Column(name="from_url")
	private String from_url;
	@Column(name="to_url")
    private String to_url;
	@Column(name="host")
    private String host;
	@Column(name="method")
    private String method;
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFrom_url() {
		return from_url;
	}
	public void setFrom_url(String from_url) {
		this.from_url = from_url;
	}
	public String getTo_url() {
		return to_url;
	}
	public void setTo_url(String to_url) {
		this.to_url = to_url;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
    
}

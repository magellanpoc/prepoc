package spring.data.rest.security.examples;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Domain object for an item managed by the company.
 *
 * @author User
 * 
 */
@Entity
@Data
@RequiredArgsConstructor
public class Item {

	private @Id @GeneratedValue Long id;
	private final String description;

	Item() {
		this.description = null;
	}
}

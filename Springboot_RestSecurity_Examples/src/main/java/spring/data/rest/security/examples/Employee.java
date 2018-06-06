package spring.data.rest.security.examples;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Domain object for an employee.
 *
 * @author User
 */
@Data
@Entity
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {

	private @Id @GeneratedValue Long id;
	private final String firstName, lastName, title;

	Employee() {
		this.firstName = null;
		this.lastName = null;
		this.title = null;
	}
}

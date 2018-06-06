package spring.data.rest.security.examples;

import org.springframework.data.repository.CrudRepository;

/**
 * This repository has no method-level security annotations. That's because it's secured at the URL level inside
 * {@link example.springdata.rest.security.SecurityConfiguration}.
 *
 * @author User
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {}

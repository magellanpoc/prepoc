package spring.data.rest.security.examples;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Collection of test cases used to verify method-level security.
 *
 * @author User
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MethodLevelSecurityTests {

	@Autowired ItemRepository itemRepository;

	@Before
	public void setUp() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void rejectsMethodInvocationsForNoAuth() {

		try {
			itemRepository.findAll();
			fail("Expected a security error");
		} catch (AuthenticationCredentialsNotFoundException e) {
			// expected
		}

		try {
			itemRepository.save(new Item("MacBook Pro"));
			fail("Expected a security error");
		} catch (AuthenticationCredentialsNotFoundException e) {
			// expected
		}

		try {
			itemRepository.delete(1L);
			fail("Expected a security error");
		} catch (AuthenticationCredentialsNotFoundException e) {
			// expected
		}
	}

	@Test
	public void rejectsMethodInvocationsForAuthWithInsufficientPermissions() {

		SecurityUtils.runAs("system", "system", "ROLE_USER");

		itemRepository.findAll();

		try {
			itemRepository.save(new Item("MacBook Pro"));
			fail("Expected a security error");
		} catch (AccessDeniedException e) {
			// expected
		}
		try {
			itemRepository.delete(1L);
			fail("Expected a security error");
		} catch (AccessDeniedException e) {
			// expected
		}
	}

	@Test
	public void allowsMethodInvocationsForAuthWithSufficientPermissions() {

		SecurityUtils.runAs("system", "system", "ROLE_USER", "ROLE_ADMIN");

		itemRepository.findAll();
		itemRepository.save(new Item("MacBook Pro"));
		itemRepository.delete(1L);
	}
}

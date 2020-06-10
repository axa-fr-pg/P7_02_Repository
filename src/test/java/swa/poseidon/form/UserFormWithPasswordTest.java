package swa.poseidon.form;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import swa.poseidon.form.UserFormWithPassword;
import swa.poseidon.log.LogService;
import swa.poseidon.model.User;

public class UserFormWithPasswordTest 
{
	private static ValidatorFactory factory;
 	private static Validator validator;

	@BeforeAll
	private static void setup()
	{
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@AfterAll
	private static void closeUp()
	{
		factory.close();
	}

	@Test
	public void givenValidParameters_newInstance_returnsEmptyViolationSet()
	{
		// GIVEN
	    String username="username";
	    String password="password CORRECT for this 1st try !";
	    String fullname="fullname";
		Integer role=12345;
		// WHEN
		UserFormWithPassword form =  new UserFormWithPassword(new User(username, password, fullname, role));
        Set<ConstraintViolation<UserFormWithPassword>> violations = validator.validate(form);
        for (ConstraintViolation<UserFormWithPassword> cv : violations) LogService.logger.debug(cv.getPropertyPath() + " " + cv.getMessage());
		// THEN
        assertTrue(violations.isEmpty());
	}

	@Test
	public void givenInvalidUsernameFullnameRole_newInstance_returnsViolationErrors()
	{
		// GIVEN
	    String username="";
	    String password="password CORRECT for this 2nd try !";
	    String fullname="";
		Integer role=-1;
		// WHEN
		UserFormWithPassword form =  new UserFormWithPassword(new User(username, password, fullname, role));
        Set<ConstraintViolation<UserFormWithPassword>> violations = validator.validate(form);
        for (ConstraintViolation<UserFormWithPassword> cv : violations) LogService.logger.debug(cv.getPropertyPath() + " " + cv.getMessage());
		// THEN
        assertEquals(3, violations.size()); // 3 and not 4 because password is in User but not in UserFormWithPassword
	}
	
	@Test
	public void givenInvalidPassword_newInstance_returnsViolationErrors()
	{
		// GIVEN
	    String username="username";
	    String password="bad";
	    String fullname="fullname";
		Integer role=12345;
		// WHEN
		UserFormWithPassword form =  new UserFormWithPassword(new User(username, password, fullname, role));
        Set<ConstraintViolation<UserFormWithPassword>> violations = validator.validate(form);
        for (ConstraintViolation<UserFormWithPassword> cv : violations) LogService.logger.debug(cv.getPropertyPath() + " " + cv.getMessage());
		// THEN
        assertEquals(4, violations.size());
	}

}

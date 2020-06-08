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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import swa.poseidon.form.UserForm;
import swa.poseidon.model.User;

public class UserFormTest 
{
    private static final Logger logger = LoggerFactory.getLogger(UserFormTest.class);

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
	    String password="password";
	    String fullname="fullname";
		Integer role=12345;
		// WHEN
		UserForm form =  new UserForm(new User(username, password, fullname, role));
        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        for (ConstraintViolation<UserForm> cv : violations) logger.info(cv.getPropertyPath() + " " + cv.getMessage());
		// THEN
        assertTrue(violations.isEmpty());
	}

	@Test
	public void givenInvalidParameters_newInstance_returnsViolationErrors()
	{
		// GIVEN
	    String username="";
	    String password="";
	    String fullname="";
		Integer role=-1;
		// WHEN
		UserForm form =  new UserForm(new User(username, password, fullname, role));
        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        for (ConstraintViolation<UserForm> cv : violations) logger.info(cv.getPropertyPath() + " " + cv.getMessage());
		// THEN
        assertEquals(3, violations.size()); // 3 and not 4 because password is in User but not in UserForm
	}
}

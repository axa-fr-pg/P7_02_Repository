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

import swa.poseidon.form.UserForm;
import swa.poseidon.log.LogService;
import swa.poseidon.model.User;

public class UserFormTest 
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
	    String fullname="fullname";
		Integer role=12345;
		// WHEN
		UserForm form =  new UserForm(new User(username, "no password in UserForm", fullname, role));
        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        for (ConstraintViolation<UserForm> cv : violations) LogService.logger.debug(cv.getPropertyPath() + " " + cv.getMessage());
		// THEN
        assertTrue(violations.isEmpty());
	}

	@Test
	public void givenInvalidParameters_newInstance_returnsViolationErrors()
	{
		// GIVEN
	    String username="";
	    String fullname="";
		Integer role=-1;
		// WHEN
		UserForm form =  new UserForm(new User(username, "no password in UserForm", fullname, role));
        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        for (ConstraintViolation<UserForm> cv : violations) LogService.logger.debug(cv.getPropertyPath() + " " + cv.getMessage());
		// THEN
        assertEquals(3, violations.size()); // 3 and not 4 because password is in User but not in UserForm
	}
}

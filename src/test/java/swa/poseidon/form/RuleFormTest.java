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

import swa.poseidon.form.RuleForm;
import swa.poseidon.log.LogService;
import swa.poseidon.model.Rule;

public class RuleFormTest 
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
		String name="name";
		String description="description";
		String json="json";
		String template="template";
		String sqlStr="sqlStr";
		String sqlPart="sqlPart";
		// WHEN
		RuleForm rating = new RuleForm(new RuleForm (new Rule(name, description, json, template, sqlStr, sqlPart)));
        Set<ConstraintViolation<RuleForm>> violations = validator.validate(rating);
        for (ConstraintViolation<RuleForm> cv : violations) LogService.logger.debug(cv.getPropertyPath() + " " + cv.getMessage());
		// THEN
        assertTrue(violations.isEmpty());
	}

	@Test
	public void givenInvalidParameters_newInstance_returnsViolationErrors()
	{
		// GIVEN
		String name="";
		String description="";
		String json="";
		String template="";
		String sqlStr="";
		String sqlPart="";
		// WHEN
		RuleForm rating = new RuleForm(new RuleForm(new Rule(name, description, json, template, sqlStr, sqlPart)));
        Set<ConstraintViolation<RuleForm>> violations = validator.validate(rating);
        for (ConstraintViolation<RuleForm> cv : violations) LogService.logger.debug(cv.getPropertyPath() + " " + cv.getMessage());
		// THEN
        assertEquals(6, violations.size());
	}
}

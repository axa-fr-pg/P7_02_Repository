package swa.poseidon.form;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
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

import swa.poseidon.form.CurvePointForm;
import swa.poseidon.model.CurvePoint;

public class CurvePointFormTest 
{
    private static final Logger logger = LoggerFactory.getLogger(CurvePointFormTest.class);

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
		Integer curveId = 1;
		BigDecimal term = new BigDecimal(1234567.0);
		BigDecimal value = new BigDecimal(87654321.0);
		// WHEN
		CurvePointForm curvePoint = new CurvePointForm(new CurvePoint(curveId, term, value));
        Set<ConstraintViolation<CurvePointForm>> violations = validator.validate(curvePoint);
        for (ConstraintViolation<CurvePointForm> cv : violations) logger.info(cv.getPropertyPath() + " " + cv.getMessage());
		// THEN
        assertTrue(violations.isEmpty());
	}

	@Test
	public void givenInvalidParameters_newInstance_returnsViolationErrors()
	{
		// GIVEN
		Integer curveId = 0;
		BigDecimal term = new BigDecimal(123456789L);
		BigDecimal value = new BigDecimal(123456789L);
		// WHEN
		CurvePointForm curvePoint = new CurvePointForm(new CurvePoint(curveId, term, value));
        Set<ConstraintViolation<CurvePointForm>> violations = validator.validate(curvePoint);
        for (ConstraintViolation<CurvePointForm> cv : violations) logger.info(cv.getPropertyPath() + " " + cv.getMessage());
		// THEN
        assertEquals(3, violations.size());
	}
}

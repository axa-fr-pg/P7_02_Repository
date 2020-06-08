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

import swa.poseidon.form.TradeForm;
import swa.poseidon.model.Trade;

public class TradeFormTest 
{
    private static final Logger logger = LoggerFactory.getLogger(TradeFormTest.class);

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
		String account="account1";
		String type="type1";
		BigDecimal buyQuantity = new BigDecimal(12345678.0);
		// WHEN
		TradeForm form =  new TradeForm(new Trade(account, type, buyQuantity));
        Set<ConstraintViolation<TradeForm>> violations = validator.validate(form);
        for (ConstraintViolation<TradeForm> cv : violations) logger.info(cv.getPropertyPath() + " " + cv.getMessage());
		// THEN
        assertTrue(violations.isEmpty());
	}

	@Test
	public void givenInvalidParameters_newInstance_returnsViolationErrors()
	{
		// GIVEN
		String account="";
		String type="";
		BigDecimal buyQuantity=new BigDecimal(0);
		// WHEN
		TradeForm form =  new TradeForm(new Trade(account, type, buyQuantity));
        Set<ConstraintViolation<TradeForm>> violations = validator.validate(form);
        for (ConstraintViolation<TradeForm> cv : violations) logger.info(cv.getPropertyPath() + " " + cv.getMessage());
		// THEN
        assertEquals(3, violations.size());
	}
}

package swa.poseidon.model;

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

import swa.poseidon.model.Bid;

public class BidTest {

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
		BigDecimal bidQuantity = new BigDecimal(12345678.0);
		// WHEN
		Bid bid =  new Bid(account, type, bidQuantity);
        Set<ConstraintViolation<Bid>> violations = validator.validate(bid);
        for (ConstraintViolation<Bid> cv : violations) System.out.println(cv.getMessage());
		// THEN
        assertTrue(violations.isEmpty());
	}

	@Test
	public void givenInvalidParameters_newInstance_returnsViolationErrors()
	{
		// GIVEN
		String account="";
		String type="";
		BigDecimal bidQuantity=new BigDecimal(0);
		// WHEN
		Bid bid =  new Bid(account, type, bidQuantity);
        Set<ConstraintViolation<Bid>> violations = validator.validate(bid);
        for (ConstraintViolation<Bid> cv : violations) System.out.println(cv.getMessage());
		// THEN
        assertEquals(3, violations.size());
	}
}

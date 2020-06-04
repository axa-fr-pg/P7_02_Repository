package swa.poseidon.model;

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

public class RatingTest 
{
    private static final Logger logger = LoggerFactory.getLogger(RatingTest.class);

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
		String moodysRating="moody";
		String standPoorRating="standard & poor";
		String fitchRating="fitch";
		Integer orderNumber=1;
		// WHEN
		Rating rating = new Rating(moodysRating, standPoorRating, fitchRating, orderNumber);
        Set<ConstraintViolation<Rating>> violations = validator.validate(rating);
        for (ConstraintViolation<Rating> cv : violations) logger.info(cv.getPropertyPath() + " " + cv.getMessage());
		// THEN
        assertTrue(violations.isEmpty());
	}

	@Test
	public void givenInvalidParameters_newInstance_returnsViolationErrors()
	{
		// GIVEN
		String moodysRating="";
		String standPoorRating="";
		String fitchRating="";
		Integer orderNumber=0;
		// WHEN
		Rating rating = new Rating(moodysRating, standPoorRating, fitchRating, orderNumber);
        Set<ConstraintViolation<Rating>> violations = validator.validate(rating);
        for (ConstraintViolation<Rating> cv : violations) logger.info(cv.getPropertyPath() + " " + cv.getMessage());
		// THEN
        assertEquals(4, violations.size());
	}
}

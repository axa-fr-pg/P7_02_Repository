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

import swa.poseidon.form.RatingForm;
import swa.poseidon.model.Rating;

public class RatingFormTest 
{
    private static final Logger logger = LoggerFactory.getLogger(RatingFormTest.class);

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
		String moodysRatingForm="moody";
		String standPoorRatingForm="standard & poor";
		String fitchRatingForm="fitch";
		Integer orderNumber=1;
		// WHEN
		RatingForm rating = new RatingForm(new Rating (moodysRatingForm, standPoorRatingForm, fitchRatingForm, orderNumber));
        Set<ConstraintViolation<RatingForm>> violations = validator.validate(rating);
        for (ConstraintViolation<RatingForm> cv : violations) logger.info(cv.getPropertyPath() + " " + cv.getMessage());
		// THEN
        assertTrue(violations.isEmpty());
	}

	@Test
	public void givenInvalidParameters_newInstance_returnsViolationErrors()
	{
		// GIVEN
		String moodysRatingForm="";
		String standPoorRatingForm="";
		String fitchRatingForm="";
		Integer orderNumber=0;
		// WHEN
		RatingForm rating = new RatingForm(new Rating(moodysRatingForm, standPoorRatingForm, fitchRatingForm, orderNumber));
        Set<ConstraintViolation<RatingForm>> violations = validator.validate(rating);
        for (ConstraintViolation<RatingForm> cv : violations) logger.info(cv.getPropertyPath() + " " + cv.getMessage());
		// THEN
        assertEquals(4, violations.size());
	}
}

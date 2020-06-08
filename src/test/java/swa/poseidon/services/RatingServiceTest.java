package swa.poseidon.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import swa.poseidon.form.RatingForm;
import swa.poseidon.model.Rating;
import swa.poseidon.repositories.RatingRepository;

@SpringBootTest
public class RatingServiceTest extends EntityServiceTest<Rating,RatingForm>
{
	@MockBean
	private RatingRepository autowiredRepository;
	
	@Autowired
	private RatingService autowiredService;
	
	@PostConstruct
	public void injectEntityProperties()
	{
		super.entityRepository = autowiredRepository;
		super.entityService = autowiredService;
		super.entity = new Rating();
		super.entityCore = entity;
	}
}

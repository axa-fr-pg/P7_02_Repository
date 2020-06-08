package swa.poseidon.integration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import swa.poseidon.form.RatingForm;
import swa.poseidon.model.Rating;
import swa.poseidon.repositories.RatingRepository;
import swa.poseidon.services.RatingService;

@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerIT extends EntityControllerIT<Rating, RatingForm>
{
	@Autowired
	private RatingRepository autowiredRepository;
	
	@Autowired
	private RatingService autowiredService;

	@Autowired
	private MockMvc autowiredMvc;
	
	@PostConstruct
	public void injectEntityPropertiesIntoSuper()
	{
		entityRepository = autowiredRepository;
		entityService = autowiredService;
		entity = new Rating();
		entityCore = entity;
		mvc = autowiredMvc;
		entityRootRequestMapping = "/ratings";
		numberOfEntityFieldsToValidate = 4;
	}
}

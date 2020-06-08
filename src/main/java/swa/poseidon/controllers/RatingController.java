package swa.poseidon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swa.poseidon.form.RatingForm;
import swa.poseidon.model.Rating;
import swa.poseidon.services.RatingService;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/ratings")
public class RatingController extends EntityController<Rating,RatingForm>
{
	@Autowired
	RatingService autowiredService;

	@PostConstruct
	public void injectService()
	{
		super.entityService = autowiredService;
	}
}

package swa.poseidon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swa.poseidon.form.BidForm;
import swa.poseidon.model.Bid;
import swa.poseidon.services.BidService;
import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/bids")
public class BidController extends EntityController<Bid, BidForm>
{
	@Autowired
	BidService autowiredService;

	@PostConstruct
	public void injectService()
	{
		super.entityService = autowiredService;
	}
}

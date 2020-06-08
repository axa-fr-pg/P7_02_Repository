package swa.poseidon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import swa.poseidon.form.TradeForm;
import swa.poseidon.model.Trade;
import swa.poseidon.services.TradeService;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/trades")
public class TradeController extends EntityController<Trade,TradeForm>
{
	@Autowired
	TradeService autowiredService;

	@PostConstruct
	public void injectService()
	{
		super.entityService = autowiredService;
	}
}

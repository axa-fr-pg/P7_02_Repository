package swa.poseidon.integration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import swa.poseidon.form.BidForm;
import swa.poseidon.model.Bid;
import swa.poseidon.repositories.BidRepository;
import swa.poseidon.services.BidService;

@SpringBootTest
@AutoConfigureMockMvc
public class BidControllerIT extends EntityControllerIT<Bid,BidForm>
{
	@Autowired
	private BidRepository autowiredRepository;
	
	@Autowired
	private BidService autowiredService;

	@Autowired
	private MockMvc autowiredMvc;
	
	@PostConstruct
	public void injectEntityPropertiesIntoSuper()
	{
		entityRepository = autowiredRepository;
		entityService = autowiredService;
		entity = new Bid();
		entityCore = entity;
		mvc = autowiredMvc;
		entityRootRequestMapping = "/bids";
		numberOfEntityFieldsToValidate = 3;
	}
}

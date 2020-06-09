package swa.poseidon.integration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import swa.poseidon.form.TradeForm;
import swa.poseidon.model.Trade;
import swa.poseidon.repositories.TradeRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerIT extends EntityControllerIT<Trade,TradeForm>
{
	@Autowired
	private TradeRepository autowiredRepository;
	
	@Autowired
	private MockMvc autowiredMvc;
	
	@PostConstruct
	public void injectEntityPropertiesIntoSuper()
	{
		entityRepository = autowiredRepository;
		entity = new Trade();
		entityCore = entity;
		mvc = autowiredMvc;
		entityRootRequestMapping = "/trades";
		numberOfEntityFieldsToValidate = 3;
	}
}

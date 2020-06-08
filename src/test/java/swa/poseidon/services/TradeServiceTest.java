package swa.poseidon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import swa.poseidon.form.TradeForm;
import swa.poseidon.model.Trade;
import swa.poseidon.repositories.TradeRepository;
import javax.annotation.PostConstruct;

@SpringBootTest
public class TradeServiceTest extends EntityServiceTest<Trade,TradeForm>
{
	@MockBean
	private TradeRepository autowiredRepository;
	
	@Autowired
	private TradeService autowiredService;
	
	@PostConstruct
	public void injectEntityProperties()
	{
		super.entityRepository = autowiredRepository;
		super.entityService = autowiredService;
		super.entity = new Trade();
		super.entityCore = entity;
	}
}

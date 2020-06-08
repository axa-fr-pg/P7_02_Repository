package swa.poseidon.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import swa.poseidon.form.RuleForm;
import swa.poseidon.model.Rule;
import swa.poseidon.repositories.RuleRepository;

@SpringBootTest
public class RuleServiceTest extends EntityServiceTest<Rule,RuleForm>
{
	@MockBean
	private RuleRepository autowiredRepository;
	
	@Autowired
	private RuleService autowiredService;
	
	@PostConstruct
	public void injectEntityProperties()
	{
		super.entityRepository = autowiredRepository;
		super.entityService = autowiredService;
		super.entity = new Rule();
		super.entityCore = entity;
	}
}

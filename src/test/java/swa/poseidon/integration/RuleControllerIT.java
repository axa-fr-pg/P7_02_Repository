package swa.poseidon.integration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import swa.poseidon.form.RuleForm;
import swa.poseidon.model.Rule;
import swa.poseidon.repositories.RuleRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class RuleControllerIT extends EntityControllerIT<Rule, RuleForm>
{
	@Autowired
	private RuleRepository autowiredRepository;
	
	@Autowired
	private MockMvc autowiredMvc;
	
	@PostConstruct
	public void injectEntityPropertiesIntoSuper()
	{
		entityRepository = autowiredRepository;
		entity = new Rule();
		entityCore = entity;
		mvc = autowiredMvc;
		entityRootRequestMapping = "/rules";
		numberOfEntityFieldsToValidate = 6;
	}
}

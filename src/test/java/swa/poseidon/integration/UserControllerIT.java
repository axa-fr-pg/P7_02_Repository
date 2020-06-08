package swa.poseidon.integration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import swa.poseidon.form.UserForm;
import swa.poseidon.model.User;
import swa.poseidon.repositories.UserRepository;
import swa.poseidon.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT extends EntityControllerIT<User,UserForm>
{
	@Autowired
	private UserRepository autowiredRepository;
	
	@Autowired
	private UserService autowiredService;

	@Autowired
	private MockMvc autowiredMvc;
	
	@PostConstruct
	public void injectEntityPropertiesIntoSuper()
	{
		entityRepository = autowiredRepository;
		entityService = autowiredService;
		entity = new User();
		entityCore = entity;
		mvc = autowiredMvc;
		entityRootRequestMapping = "/users";
		numberOfEntityFieldsToValidate = 3;
	}
}

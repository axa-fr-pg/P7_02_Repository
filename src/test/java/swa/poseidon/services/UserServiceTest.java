package swa.poseidon.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import swa.poseidon.form.UserForm;
import swa.poseidon.model.User;
import swa.poseidon.repositories.UserRepository;
import javax.annotation.PostConstruct;

@SpringBootTest
public class UserServiceTest extends EntityServiceTest<User,UserForm>
{
	@MockBean
	private UserRepository autowiredRepository;
	
	@Autowired
	private UserService autowiredService;
	
	@PostConstruct
	public void injectEntityProperties()
	{
		super.entityRepository = autowiredRepository;
		super.entityService = autowiredService;
		super.entity = new User();
		super.entityCore = entity;
	}
}

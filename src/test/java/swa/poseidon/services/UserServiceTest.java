package swa.poseidon.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import swa.poseidon.form.UserForm;
import swa.poseidon.form.UserFormWithPassword;
import swa.poseidon.model.User;
import swa.poseidon.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

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
	
	@Test
	public void givenValidForm_createByFormWithPassword_generatesNewId()
	{
		// GIVEN
		User expected =  (User) entityCore.newValidTestEntityWithIdZero(1);
		UserFormWithPassword given =  new UserFormWithPassword(expected);
		expected.setId(1);
		when(entityRepository.save(any(User.class))).thenReturn(expected);
		// WHEN
		User resultUser = autowiredService.createByFormWithPassword(given);
		// THEN
		assertNotNull(resultUser);
		assertEquals(1, resultUser.getUserId());
	}
	
	@Test
	public void givenForm_updateByForm_returnsSavedEntity()
	{
		// GIVEN
		User expected =  (User) entityCore.newValidTestEntityWithGivenId(1);
		UserFormWithPassword given =  new UserFormWithPassword(expected);
		when(entityRepository.findById(1)).thenReturn((Optional<User>) Optional.of(expected));
		when(entityRepository.save(any(User.class))).thenReturn(expected);
		// WHEN
		User resultUser = autowiredService.updateByForm(given);
		// THEN
		assertNotNull(resultUser);
		assertEquals(1, resultUser.getUserId());
	}
}

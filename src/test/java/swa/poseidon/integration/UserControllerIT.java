package swa.poseidon.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import swa.poseidon.controllers.ExceptionManager;
import swa.poseidon.form.UserForm;
import swa.poseidon.form.UserFormWithPassword;
import swa.poseidon.log.CacheConfigurationService;
import swa.poseidon.model.User;
import swa.poseidon.repositories.UserRepository;
import swa.poseidon.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT
{
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserService userService;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private ObjectMapper objectMapper;
	
    User loginUser;

	private User entityCore = new User();
	private static final String entityRootRequestMapping = "/users";
	
	private User insertNewTestEntityIntoDatabase(int index)
	{
		return repository.save((User)entityCore.newValidTestEntityWithIdZero(index));
	}

	private void prepareDatabase()
	{
		repository.deleteAll();
		loginUser = (User) (new User()).newValidTestEntityWithIdZero(1);
		UserFormWithPassword form = loginUser.toFormWithPassword();		
		userService.createByFormWithPassword(form);
	}
	
	private void authenticate() throws Exception
	{
		mvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).addFilters(new CacheConfigurationService()).build();
		SecurityContext securityContext = (SecurityContext) mvc
				.perform(formLogin("/processPoseidonLogin")
							.user(loginUser.getUsername())
							.password(loginUser.getPassword()))
				.andExpect(authenticated())
				.andReturn().getRequest().getSession()
				.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		SecurityContextHolder.setContext(securityContext);
	}
	
	@BeforeEach
	public void setup() throws Exception
	{
		prepareDatabase();
		authenticate();
	}

	@Test
	public void givenEntityList_readAll_returnsCorrectList() throws Exception 
	{
		// GIVEN
		User e1 =  insertNewTestEntityIntoDatabase(2);
		User e2 =  insertNewTestEntityIntoDatabase(3);
		User e3 =  insertNewTestEntityIntoDatabase(4);
		// WHEN
		String responseString = mvc.perform(get(entityRootRequestMapping+"/list"))
				//.andDo(print())
				.andReturn().getResponse().getContentAsString();
		JavaType expectedResultType = objectMapper.getTypeFactory().constructCollectionType(List.class, UserForm.class);
		List<UserForm> responseObject = objectMapper.readValue(responseString, expectedResultType);
		// THEN
		assertNotNull(responseObject); 
		assertEquals(4, responseObject.size());
		UserForm f1 = responseObject.get(0);
		assertTrue(f1.getUsername().equals(loginUser.getUsername()));
		UserForm f2 = responseObject.get(1);
		assertTrue(f2.matches(e1));
		UserForm f3 = responseObject.get(2);
		assertTrue(f3.matches(e2));
		UserForm f4 = responseObject.get(3);
		assertTrue(f4.matches(e3));
	}
	
	@Test
	public void givenValidForm_post_returnsCreatedEntity() throws Exception 
	{
		// GIVEN
		int index = 2;
		User givenUser = (User) entityCore.newValidTestEntityWithIdZero(index);
		UserFormWithPassword givenForm = (UserFormWithPassword) givenUser.toFormWithPassword();
		User expectedEntity = (User) entityCore.newValidTestEntityWithIdZero(index);
		String json = objectMapper.writeValueAsString(givenForm);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(entityRootRequestMapping+"/add")
			.contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON).with(csrf());
		// WHEN & THEN
		String responseString = mvc.perform(builder)
				//.andDo(print())
				.andExpect(status().isCreated())
				.andReturn().getResponse().getContentAsString();
		Object responseObject = objectMapper.readValue(responseString, givenForm.getClass());
		assertNotNull(responseObject); 
		UserForm resultForm = (UserForm) responseObject;
		expectedEntity.setId(resultForm.id());
		assertTrue(resultForm.matches(expectedEntity));
	}
	
	@Test
	public void givenInvalidForm_post_returnsBadRequestStatusAndMessages() throws Exception 
	{
		// GIVEN
		User givenUser = (User) entityCore.newInvalidTestEntity();
		UserFormWithPassword givenForm = givenUser.toFormWithPassword();
		String json = objectMapper.writeValueAsString(givenForm);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(entityRootRequestMapping+"/add")
			.contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON).with(csrf());
		// WHEN & THEN
		String responseString = mvc.perform(builder)
			//.andDo(print())
			.andExpect(status().isBadRequest())
			.andReturn().getResponse().getContentAsString();
		@SuppressWarnings("unchecked")
		ArrayList<String> responseObject = objectMapper.readValue(responseString, ArrayList.class);
		assertNotNull(responseObject); 
		assertEquals(8, responseObject.size());
	}
	
	@Test
	public void givenNotAuthenticated_post_returnsOk() throws Exception
	{
		// GIVEN
		mvc.perform(logout()).andExpect(unauthenticated());
		// WHEN & THEN
		givenValidForm_post_returnsCreatedEntity();		
	}
	
	@Test
	public void givenCorrectForm_put_returnsUpdatedEntity() throws Exception 
	{
		// GIVEN
		User givenUser =  insertNewTestEntityIntoDatabase(2);
		UserFormWithPassword givenForm = givenUser.toFormWithPassword();
		Integer id = givenForm.id();
		String json = objectMapper.writeValueAsString(givenForm);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(entityRootRequestMapping+"/update/" + id)
			.contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON).with(csrf());
		// WHEN & THEN
		String responseString = mvc.perform(builder)
				//.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		Object responseObject = objectMapper.readValue(responseString, givenForm.getClass());
		assertNotNull(responseObject); 
		UserForm resultForm = (UserForm) responseObject;
		assertTrue(resultForm.matches(givenUser));
	}
	
	@Test
	public void givenInvalidId_put_throwsNoSuchElementException() throws Exception 
	{
		// GIVEN
		int index = 1;
		User givenUser = (User) entityCore.newValidTestEntityWithGivenId(index); // prepared but not saved
		UserFormWithPassword givenForm = (UserFormWithPassword) givenUser.toFormWithPassword();
		String json = objectMapper.writeValueAsString(givenForm);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(entityRootRequestMapping+"/update/" + index)
			.contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON).with(csrf());
		// WHEN
		String responseString = mvc.perform(builder)
				//.andDo(print())
				.andReturn().getResponse().getContentAsString();
		// THEN
		assertEquals(ExceptionManager.MESSAGE_NOT_SUCH_ELEMENT, responseString);
	}
	
	@Test
	public void givenValidId_read_returnsCorrectForm() throws Exception 
	{
		// GIVEN
		User givenUser =  insertNewTestEntityIntoDatabase(2);
		Integer id = givenUser.getUserId();
		// WHEN & THEN
		String responseString = mvc.perform(get(entityRootRequestMapping+"/read/"+id))
				//.andDo(print())
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		Object responseObject = objectMapper.readValue(responseString, UserForm.class);
		assertNotNull(responseObject); 
		UserForm resultForm = (UserForm) responseObject;
		assertTrue(resultForm.matches(givenUser));
	}	

	@Test
	public void givenWrongId_read_throwsNoSuchElementException() throws Exception 
	{
		// GIVEN
		Integer id = 999;
		// WHEN 
		String responseString = mvc.perform(get(entityRootRequestMapping+"/read/"+id))
				//.andDo(print())
				.andReturn().getResponse().getContentAsString();
		// THEN
		assertEquals(ExceptionManager.MESSAGE_NOT_SUCH_ELEMENT, responseString);
	}	

	@Test
	public void givenValidId_delete_removesEntity() throws Exception 
	{
		// GIVEN
		User givenUser =  insertNewTestEntityIntoDatabase(2);
		Integer id = givenUser.getUserId();
		// WHEN
		String responseString = mvc.perform(delete(entityRootRequestMapping+"/delete/"+id).with(csrf()))				
				//.andDo(print())
				.andReturn().getResponse().getContentAsString();
		Boolean responseObject = objectMapper.readValue(responseString, Boolean.class);
		// THEN
		assertTrue(responseObject);
	}

	@Test
	public void givenWrongId_delete_returnsNoSuchElementMessage() throws Exception 
	{
		// GIVEN
		Integer id = 999;
		// WHEN 
		String responseString = mvc.perform(delete(entityRootRequestMapping+"/delete/"+id).with(csrf()))
				//.andDo(print())
				.andReturn().getResponse().getContentAsString();
		// THEN
		assertEquals(ExceptionManager.MESSAGE_NOT_SUCH_ELEMENT, responseString);
	}
	
	@Test
	public void givenNotAuthenticated_delete_returnsDenied() throws Exception
	{
		// GIVEN
		mvc.perform(logout()).andExpect(unauthenticated());
		User givenUser =  insertNewTestEntityIntoDatabase(2);
		Integer id = givenUser.getUserId();
		// WHEN
		mvc.perform(delete(entityRootRequestMapping+"/delete/"+id).with(csrf()))
			//.andDo(print())
		// THEN
			.andExpect(status().is3xxRedirection());
	}
}

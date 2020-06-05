package swa.poseidon.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;

import swa.poseidon.form.BidForm;
import swa.poseidon.form.BidFormList;
import swa.poseidon.model.Bid;
import swa.poseidon.repositories.BidRepository;
import swa.poseidon.services.BidService;
import swa.poseidon.services.EntityServiceTest;

@SpringBootTest
@AutoConfigureMockMvc
public class BidControllerIT {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private BidRepository bidRepository;
	
	private Bid saveNewTestBidToRepository(int index)
	{
		return bidRepository.save(EntityServiceTest.newTestBidWithIdZero(index));
	}
	
	@BeforeEach
	public void cleanDataBase()
	{
		bidRepository.deleteAll();
	}

	@Test
	public void givenBidList_readAll_returnsCorrectList() throws Exception 
	{
		// GIVEN
		Bid b1 =  saveNewTestBidToRepository(1);
		Bid b2 =  saveNewTestBidToRepository(2);
		Bid b3 =  saveNewTestBidToRepository(3);		
		// WHEN
		String responseString = mvc.perform(get("/bids/list")).andDo(print()).andReturn().getResponse().getContentAsString();
		BidFormList responseObject = objectMapper.readValue(responseString, BidFormList.class);
		// THEN
		assertNotNull(responseObject); 
		assertEquals(3, responseObject.getBidFormList().size());
		assertTrue(responseObject.getBidFormList().get(0).matches(b1));
		assertTrue(responseObject.getBidFormList().get(1).matches(b2));
		assertTrue(responseObject.getBidFormList().get(2).matches(b3));
	}
	
	@Test
	public void givenBidForm_post_returnsCreatedBidForm() throws Exception 
	{
		// GIVEN
		Bid newBid =  EntityServiceTest.newTestBidWithIdZero(1);
		String json = objectMapper.writeValueAsString(newBid);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/bids/add")
			.contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON);
		// WHEN & THEN
		mvc.perform(builder).andDo(print()).andExpect(status().isCreated())
			.andExpect(jsonPath("$.bidId").value(greaterThan(0))) 
			.andExpect(jsonPath("$.account").value("account1"))
			.andExpect(jsonPath("$.type").value("type1")) 
			.andExpect(jsonPath("$.bidQuantity").value(1));
	}
	
	@Test
	public void givenCorrectBidForm_put_returnsUpdatedBidForm() throws Exception 
	{
		// GIVEN
		Bid initialBid =  saveNewTestBidToRepository(1);
		Integer id = initialBid.getBidId();
		String json = objectMapper.writeValueAsString(initialBid.toForm());
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/bids/update/" + id)
			.contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON);
		// WHEN & THEN
		mvc.perform(builder).andDo(print()).andExpect(status().isOk())
			.andExpect(jsonPath("$.bidId").value(id)) 
			.andExpect(jsonPath("$.account").value("account1"))
			.andExpect(jsonPath("$.type").value("type1")) 
			.andExpect(jsonPath("$.bidQuantity").value(1));
	}
	
	@Test
	public void givenInvalidId_put_throwsNoSuchElementException() throws Exception 
	{
		// GIVEN
		Bid initialBid =  EntityServiceTest.newTestBidWithGivenId(1);
		String json = objectMapper.writeValueAsString(initialBid.toForm());
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/bids/update/" + 1)
			.contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON);
		// WHEN & THEN
		assertThatExceptionOfType(NestedServletException.class)
	    	.isThrownBy(() -> mvc.perform(builder))
	    	.withRootCauseInstanceOf(NoSuchElementException.class);
	}
	
	@Test
	public void givenValidBidId_read_returnsCorrectBidForm() throws Exception 
	{
		// GIVEN
		Bid initialBid =  saveNewTestBidToRepository(1);
		Integer id = initialBid.getBidId();
		// WHEN
		String responseString = mvc.perform(get("/bids/read/"+id)).andDo(print()).andReturn().getResponse().getContentAsString();
		BidForm responseObject = objectMapper.readValue(responseString, BidForm.class);
		// THEN
		assertNotNull(responseObject); 
		assertTrue(responseObject.matches(initialBid));
	}	

	@Test
	public void givenWrongBidId_read_throwsNoSuchElementException() throws Exception 
	{
		// GIVEN
		Integer id = 999;
		// WHEN & THEN
		assertThatExceptionOfType(NestedServletException.class)
	    	.isThrownBy(() -> mvc.perform(get("/bids/read/"+id)))
	    	.withRootCauseInstanceOf(NoSuchElementException.class);
	}	

}

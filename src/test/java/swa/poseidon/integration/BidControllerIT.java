package swa.poseidon.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.Matchers.hasProperty;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;

import swa.poseidon.form.BidForm;
import swa.poseidon.form.BidFormList;
import swa.poseidon.model.Bid;
import swa.poseidon.repositories.BidRepository;
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
	public void givenBidForm_post_returnsCorrectBid() throws Exception 
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
}

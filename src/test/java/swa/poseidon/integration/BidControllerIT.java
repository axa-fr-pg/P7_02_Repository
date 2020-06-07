package swa.poseidon.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import swa.poseidon.controllers.ExceptionManager;
import swa.poseidon.form.BidForm;
import swa.poseidon.model.Bid;
import swa.poseidon.repositories.BidRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class BidControllerIT {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private BidRepository bidRepository;
	
	private Bid bid = new Bid();
	
	private Bid saveNewTestBidToRepository(int index)
	{
		return bidRepository.save((Bid)bid.newValidTestEntityWithGivenId(index));
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
		JavaType expectedResultType = objectMapper.getTypeFactory().constructCollectionType(List.class, BidForm.class);
		List<BidForm> responseObject = objectMapper.readValue(responseString, expectedResultType);
		// THEN
		assertNotNull(responseObject); 
		assertEquals(3, responseObject.size());
		BidForm f1 = (BidForm) responseObject.get(0);
		assertTrue(f1.matches(b1));
		BidForm f2 = (BidForm) responseObject.get(1);
		assertTrue(f2.matches(b2));
		BidForm f3 = (BidForm) responseObject.get(2);
		assertTrue(f3.matches(b3));
	}
	
	@Test
	public void givenValidBidForm_post_returnsCreatedBidForm() throws Exception 
	{
		// GIVEN
		BidForm form =  bid.newValidTestEntityWithIdZero(1).toForm();
		String json = objectMapper.writeValueAsString(form);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/bids/add")
			.contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON);
		// WHEN & THEN
		mvc.perform(builder).andDo(print()).andExpect(status().isCreated())
			.andExpect(jsonPath("$.bidId").value(greaterThan(0))) 
			.andExpect(jsonPath("$.account").value(form.getAccount()))
			.andExpect(jsonPath("$.type").value(form.getType())) 
			.andExpect(jsonPath("$.bidQuantity").value(form.getBidQuantity()));
	}
	
	@Test
	public void givenInvalidBidForm_post_returnsBadRequestStatusAndMessages() throws Exception 
	{
		// GIVEN
		BidForm form = new Bid("", "", new BigDecimal(0)).toForm();
		String json = objectMapper.writeValueAsString(form);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/bids/add")
			.contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON);
		// WHEN & THEN
		String responseString = mvc.perform(builder).andDo(print())
			.andExpect(status().isBadRequest())
			.andReturn().getResponse().getContentAsString();
		@SuppressWarnings("unchecked")
		ArrayList<String> responseObject = objectMapper.readValue(responseString, ArrayList.class);
		assertNotNull(responseObject); 
		assertEquals(3, responseObject.size());
	}
	
	@Test
	public void givenCorrectBidForm_put_returnsUpdatedBidForm() throws Exception 
	{
		// GIVEN
		BidForm form =  saveNewTestBidToRepository(1).toForm();
		Integer id = form.getBidId();
		String json = objectMapper.writeValueAsString(form);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/bids/update/" + id)
			.contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON);
		// WHEN & THEN
		mvc.perform(builder).andDo(print()).andExpect(status().isOk())
			.andExpect(jsonPath("$.bidId").value(id)) 
			.andExpect(jsonPath("$.account").value(form.getAccount()))
			.andExpect(jsonPath("$.type").value(form.getType())) 
			.andExpect(jsonPath("$.bidQuantity").value(form.getBidQuantity()));
	}
	
	@Test
	public void givenInvalidId_put_throwsNoSuchElementException() throws Exception 
	{
		// GIVEN
		BidForm form =  bid.newValidTestEntityWithGivenId(1).toForm();
		String json = objectMapper.writeValueAsString(form);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put("/bids/update/" + 1)
			.contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON);
		// WHEN
		String responseString = mvc.perform(builder).andDo(print()).andReturn().getResponse().getContentAsString();
		// THEN
		assertEquals(ExceptionManager.MESSAGE_NOT_SUCH_ELEMENT, responseString);

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
		// WHEN 
		String responseString = mvc.perform(get("/bids/read/"+id)).andDo(print()).andReturn().getResponse().getContentAsString();
		// THEN
		assertEquals(ExceptionManager.MESSAGE_NOT_SUCH_ELEMENT, responseString);
	}	

	@Test
	public void givenValidBidId_delete_removesBid() throws Exception 
	{
		// GIVEN
		Bid initialBid =  saveNewTestBidToRepository(1);
		Integer id = initialBid.getBidId();
		// WHEN
		String responseString = mvc.perform(delete("/bids/delete/"+id)).andDo(print()).andReturn().getResponse().getContentAsString();
		Boolean responseObject = objectMapper.readValue(responseString, Boolean.class);
		// THEN
		assertTrue(responseObject);
	}	

	@Test
	public void givenWrongBidId_delete_returnsNoSuchElementMessage() throws Exception 
	{
		// GIVEN
		Integer id = 999;
		// WHEN 
		String responseString = mvc.perform(delete("/bids/delete/"+id)).andDo(print()).andReturn().getResponse().getContentAsString();
		// THEN
		assertEquals(ExceptionManager.MESSAGE_NOT_SUCH_ELEMENT, responseString);
	}	
}

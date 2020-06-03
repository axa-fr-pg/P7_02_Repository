package swa.poseidon.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Locale;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
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
import org.springframework.ui.Model;

import swa.poseidon.model.Bid;
import swa.poseidon.repositories.BidRepository;
import swa.poseidon.services.BidServiceTest;

@SpringBootTest
@AutoConfigureMockMvc
public class BidControllerIT {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private BidRepository bidRepository;
	
	private Bid addBidForTest(int index)
	{
		return bidRepository.save(BidServiceTest.newBidForTest(index));
	}
	
	@BeforeEach
	public void cleanDataBase()
	{
		bidRepository.deleteAll();
	}
	
	@Test
	public void givenBidList_getAll_returnsCorrectList() throws Exception 
	{
		// GIVEN
		Bid b1 =  addBidForTest(1);
		Bid b2 =  addBidForTest(2);
		Bid b3 =  addBidForTest(3);		
		// WHEN & THEN
		mvc.perform(get("/bids/list")).andDo(print())
		   .andExpect(status().isOk())
		   .andExpect(view().name("/bids/list"))
		   .andExpect(model().size(1)) // one single attribute passed as parameter
		   .andExpect(model().attribute("bids", hasSize(3))) // our three test bids
		   .andExpect(model().attribute("bids", hasItem(allOf(
				hasProperty("bidId", is(String.valueOf(b1.getBidId()))),
				hasProperty("account", is(b1.getAccount())),
				hasProperty("type", is(b1.getType())),
				hasProperty("bidQuantity", is(String.format(Locale.US, "%.1f", b1.getBidQuantity())))
			))))/*
		   .andExpect(model().attribute("bids", hasItem(allOf(
				hasProperty("bidId", is(b2.getBidId())),
				hasProperty("account", is(b2.getAccount())),
				hasProperty("type", is(b2.getType())),
				hasProperty("bidQuantity", is(String.format(Locale.US, "%.1f", b2.getBidQuantity())))
			))))
		   .andExpect(model().attribute("bids", hasItem(allOf(
				hasProperty("bidId", is(b3.getBidId())),
				hasProperty("account", is(b3.getAccount())),
				hasProperty("type", is(b3.getType())),
				hasProperty("bidQuantity", is(String.format(Locale.US, "%.1f", b3.getBidQuantity())))
			))))*/
		   ;
	}
	
	@Test
	public void givenBid_add_returnsCorrectList() throws Exception 
	{
		// GIVEN
		Bid b1 =  addBidForTest(1);
		Bid b2 =  addBidForTest(2);
		Bid b3 =  BidServiceTest.newBidForTest(3);
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
			.post("/bids/add")
			.contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
			.accept(MediaType.APPLICATION_FORM_URLENCODED)
            .param("account", b3.getAccount())
            .param("type", b3.getType())
            .param("bidQuantity", b3.getBidQuantity().toString())
            ;
		// WHEN & THEN
		mvc.perform(builder)
			.andDo(print())
		   	.andExpect(status().isCreated())
		   	.andExpect(view().name("/bids/list"))
		   	.andExpect(model().size(1)) // one single attribute passed as parameter
		   	;
	}
}

package swa.poseidon.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasProperty;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import swa.poseidon.form.BidForm;
import swa.poseidon.model.Bid;
import swa.poseidon.repositories.BidRepository;
import swa.poseidon.services.BidService;
import swa.poseidon.services.BidServiceTest;

@SpringBootTest
@AutoConfigureMockMvc
public class BidControllerIT {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private BidRepository bidRepository;
	
	@Autowired
	private BidService bidService;

	private Bid addBidForTest(int index)
	{
		return bidRepository.save(BidServiceTest.newBidForTest(index));
	}
	
	@Test
	public void givenBidList_getAll_returnsCorrectList() throws Exception {
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
					hasProperty("bidId", is(b1.getBidId())),
					hasProperty("account", is(b1.getAccount())),
					hasProperty("type", is(b1.getType())),
					hasProperty("bidQuantity", is(b1.getBidQuantity()))
				))))
		   .andExpect(model().attribute("bids", hasItem(allOf(
					hasProperty("bidId", is(b2.getBidId())),
					hasProperty("account", is(b2.getAccount())),
					hasProperty("type", is(b2.getType())),
					hasProperty("bidQuantity", is(b2.getBidQuantity()))
				))))
		   .andExpect(model().attribute("bids", hasItem(allOf(
					hasProperty("bidId", is(b3.getBidId())),
					hasProperty("account", is(b3.getAccount())),
					hasProperty("type", is(b3.getType())),
					hasProperty("bidQuantity", is(b3.getBidQuantity()))
				))))
		   ;
	}
}

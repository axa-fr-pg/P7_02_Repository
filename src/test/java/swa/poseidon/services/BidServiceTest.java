package swa.poseidon.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import swa.poseidon.form.BidForm;
import swa.poseidon.model.Bid;
import swa.poseidon.repositories.BidRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SpringBootTest
public class BidServiceTest {

	@MockBean
	private BidRepository bidRepository;
	
	@Autowired
	private BidService bidService;

	static public Bid newBidForTest(int index) 
	{
		return new Bid (index, "account"+index, "type"+index, 
				new BigDecimal(index*11), new BigDecimal(index*12), 
				new BigDecimal(13.0 * index), new BigDecimal(14.0 * index),	
				"benchmark"+index, new Date(15*index), "comment"+index, 
				"security"+index, 16*index, "trader"+index, "book"+index, "creationName"+index, 
				new Date(17*index), "revisionName"+index, new Date(18*index), 
				"dealName"+index, "dealType"+index,	19, "side"+index);
	}
		
	@Test
	public void givenBidList_getAll_returnsCorrectList() {
		// GIVEN
		Bid b1 =  newBidForTest(1);
		Bid b2 =  newBidForTest(2);
		Bid b3 =  newBidForTest(3);
		List<Bid> bidList = Arrays.asList(b1, b2, b3);
		when(bidRepository.findAll()).thenReturn(bidList);
		// WHEN
		List<BidForm> bidFormList = bidService.getAll();
		// THEN
		assertNotNull(bidFormList);
		assertEquals(3, bidFormList.size());
		BidForm bf1 = bidFormList.get(0);
		BidForm bf2 = bidFormList.get(1);
		BidForm bf3 = bidFormList.get(2);
		assertEquals(b1.getBidId(), bf1.getBidId());
		assertEquals(b2.getAccount(), bf2.getAccount());
		assertEquals(b3.getType(), bf3.getType());
		assertEquals(b1.getBidId(), bf1.getBidId());
		assertEquals(b2.getAccount(), bf2.getAccount());
		assertEquals(b1.getBidQuantity(), bf1.getBidQuantity());
	}
}

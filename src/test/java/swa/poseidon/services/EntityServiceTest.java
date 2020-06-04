package swa.poseidon.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import swa.poseidon.form.BidForm;
import swa.poseidon.model.Bid;
import swa.poseidon.repositories.BidRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class EntityServiceTest
{
	@MockBean
	private BidRepository bidRepository;
	
	@Autowired
	private BidService bidService;
	
	static public Bid newValidBidForTest(int index) 
	{
		return new Bid ("account"+index, "type"+index, new BigDecimal(index*1.0));
	}
			
	@Test
	public void givenBidList_readAll_returnsCorrectList() {
		// GIVEN
		Bid b1 =  newValidBidForTest(1);
		Bid b2 =  newValidBidForTest(2);
		Bid b3 =  newValidBidForTest(3);
		List<Bid> bidList = Arrays.asList(b1, b2, b3);
		when(bidRepository.findAll()).thenReturn(bidList);
		// WHEN
		List<BidForm> bidFormList = bidService.readAll();
		// THEN
		assertNotNull(bidFormList);
		assertEquals(3, bidFormList.size());
		BidForm bf1 = bidFormList.get(0);
		BidForm bf2 = bidFormList.get(1);
		BidForm bf3 = bidFormList.get(2);
		assertEquals(b1.getBidId(), bf1.getBidId());
		assertEquals(b2.getAccount(), bf2.getAccount());
		assertEquals(b3.getType(), bf3.getType());
		assertEquals(b1.getBidQuantity(), bf1.getBidQuantity());
	}
	
	@Test
	public void givenNewBid_create_generatesNewId()
	{
		// GIVEN
		Bid b1 =  newValidBidForTest(1);
		Bid b1Saved =  new Bid(b1.getAccount(), b1.getType(), b1.getBidQuantity());
		b1Saved.setBidId(1);
		when(bidRepository.save(b1)).thenReturn(b1Saved);
		// WHEN
		Bid result = bidService.create(b1);
		// THEN
		assertNotNull(result);
		assertEquals(b1Saved.getBidId(), result.getBidId());
	}
	
	@Test
	public void givenBidFound_read_returnsCorrectBid() {
		// GIVEN
		Bid b1 =  newValidBidForTest(1);
		when(bidRepository.findById(b1.getBidId())).thenReturn(Optional.of(b1));
		// WHEN
		Bid result = bidService.read(b1.getBidId());
		// THEN
		assertNotNull(result);
		assertEquals(b1.getBidId(), result.getBidId());
	}
	
	@Test
	public void givenBidNotFound_read_returnsNoBid() {
		// GIVEN
		Bid b1 =  newValidBidForTest(1);
		when(bidRepository.findById(b1.getBidId())).thenReturn(null);
		// WHEN
		Bid result = bidService.read(b1.getBidId());
		// THEN
		assertNull(result);
	}
	
	@Test
	public void givenBidFound_delete_returnsTrue() {
		// GIVEN
		Bid b1 =  newValidBidForTest(1);
		when(bidRepository.findById(b1.getBidId())).thenReturn(Optional.of(b1));
		// WHEN
		boolean result = bidService.delete(b1.getBidId());
		// THEN
		assertTrue(result);
	}
	
	@Test
	public void givenBidNotFound_delete_returnsFalse() {
		// GIVEN
		Bid b1 =  newValidBidForTest(1);
		when(bidRepository.findById(b1.getBidId())).thenReturn(null);
		// WHEN
		boolean result = bidService.delete(b1.getBidId());
		// THEN
		assertFalse(result);
	}	
}

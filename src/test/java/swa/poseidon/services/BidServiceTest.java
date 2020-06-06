package swa.poseidon.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.ArgumentMatchers.any;
import swa.poseidon.form.BidForm;
import swa.poseidon.model.Bid;
import swa.poseidon.repositories.BidRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
public class BidServiceTest
{
	@MockBean
	private BidRepository bidRepository;
	
	@Autowired
	private BidService bidService;
	
	static public Bid newTestBidWithIdZero(int index) 
	{
		return new Bid ("account"+index, "type"+index, new BigDecimal(index*1.0));
	}
			
	static public Bid newTestBidWithGivenId(Integer id) 
	{
		Bid b = new Bid ("account"+id, "type"+id, new BigDecimal(id*1.0));
		b.setId(id);
		return b;
	}
	
	@BeforeEach
	public void cleanDataBase()
	{
		bidRepository.deleteAll();
	}
	
	@Test
	public void givenBidList_readAll_returnsCorrectList() {
		// GIVEN
		Bid b1 =  newTestBidWithIdZero(1);
		Bid b2 =  newTestBidWithIdZero(2);
		Bid b3 =  newTestBidWithIdZero(3);
		List<Bid> bidList = (List<Bid>) Arrays.asList(b1, b2, b3);
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
		BidForm given =  newTestBidWithIdZero(1).toForm();
		Bid expected =  newTestBidWithGivenId(1);
		when(bidRepository.save(any(Bid.class))).thenReturn(expected);
		// WHEN
		Bid result = bidService.create(given);
		// THEN
		assertNotNull(result);
		assertEquals(expected.getBidId(), result.getBidId());
	}
	
	@Test
	public void givenBidFound_read_returnsCorrectBid() {
		// GIVEN
		Bid expected =  newTestBidWithGivenId(1);
		when(bidRepository.findById(expected.getBidId())).thenReturn(Optional.of(expected));
		// WHEN
		Bid result = bidService.read(expected.getBidId());
		// THEN
		assertNotNull(result);
		assertEquals(expected.getBidId(), result.getBidId());
	}
	
	@Test
	public void givenBidNotFound_read_throwsNoSuchElementException() {
		// GIVEN
		Integer id = 999;
		when(bidRepository.findById(id)).thenReturn(Optional.empty());
		// WHEN & THEN
		assertThrows(NoSuchElementException.class, () -> bidService.read(id));
	}
	
	@Test
	public void givenBid_update_savesBid()
	{
		// GIVEN
		Bid expected =  newTestBidWithGivenId(1);
		BidForm given =  expected.toForm();
		when(bidRepository.findById(1)).thenReturn((Optional<Bid>) Optional.of(expected));
		when(bidRepository.save(any(Bid.class))).thenReturn(expected);
		// WHEN
		Bid result = bidService.update(given);
		// THEN
		assertNotNull(result);
		assertEquals(expected.getBidId(), result.getBidId());
	}
		
	
	@Test
	public void givenBidFound_delete_returnsTrue() {
		// GIVEN
		Bid expected =  newTestBidWithGivenId(1);
		when(bidRepository.findById(expected.getBidId())).thenReturn(Optional.of(expected));
		// WHEN
		boolean result = bidService.delete(expected.getBidId());
		// THEN
		assertTrue(result);
	}
	
	@Test
	public void givenBidNotFound_delete_throwsNoSuchElementException() {
		// GIVEN
		Integer id = 999;
		when(bidRepository.findById(id)).thenReturn(Optional.empty());
		// WHEN & THEN
		assertThrows(NoSuchElementException.class, () -> bidService.delete(id));
	}	
}

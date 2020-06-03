package swa.poseidon.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import swa.poseidon.form.BidForm;
import swa.poseidon.model.Bid;
import swa.poseidon.repositories.BidRepository;

@Service
@Transactional
public class BidServiceImpl implements BidService 
{
	@Autowired BidRepository bidRepository;

	@Override
	public List<BidForm> readAll() {
		List<Bid> bidList = bidRepository.findAll();
		List<BidForm> bidFormList = new ArrayList<BidForm>();
		for (Bid b : bidList) bidFormList.add(new BidForm(b));
		return bidFormList;
	}

	@Override
	public Bid create(Bid bid) 
	{
		bid.setBidId(0);
		return bidRepository.save(bid);
	}

	@Override
	public Bid read(Integer bidId) 
	{
		Optional<Bid> bid = bidRepository.findById(bidId);
		if (bid == null) return null;
		else return bid.get();
	}

	@Override
	public Bid update(Bid bid) 
	{
		return bidRepository.save(bid);
	}

	@Override
	public boolean delete(Integer bidId) {
		Bid bid = read(bidId);
		if (bid == null) return false;
		bidRepository.delete(bid);
		return true;
	}
}

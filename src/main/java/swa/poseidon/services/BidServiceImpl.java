package swa.poseidon.services;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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
	public List<BidForm> getAll() {
		List<Bid> bidList = bidRepository.findAll();
		List<BidForm> bidFormList = new ArrayList<BidForm>();
		for (Bid b : bidList) bidFormList.add(new BidForm(b));
		return bidFormList;
	}

	@Override
	public Bid add(@Valid Bid bid) 
	{
		Bid b = new Bid(bid); // returns Bid with id == 0
		return bidRepository.save(b);
	}

}

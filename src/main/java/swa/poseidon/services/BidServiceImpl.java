package swa.poseidon.services;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import swa.poseidon.form.BidForm;
import swa.poseidon.model.Bid;
import swa.poseidon.repositories.BidRepository;

@Service
public class BidServiceImpl implements BidService 
{
	@Autowired BidRepository bidRepository;

	@Override
	public List<BidForm> getAll() {
		// TODO Auto-generated method stub
		List<Bid> bidList = bidRepository.findAll();
		List<BidForm> bidFormList = new ArrayList<BidForm>();
		for (Bid b : bidList) bidFormList.add(new BidForm(b));
		return bidFormList;
	}

	@Override
	public Bid save(@Valid Bid bid) {
		return bidRepository.save(bid);
	}

}

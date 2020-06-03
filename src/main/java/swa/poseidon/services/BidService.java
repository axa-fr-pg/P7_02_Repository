package swa.poseidon.services;

import java.util.List;

import swa.poseidon.form.BidForm;
import swa.poseidon.model.Bid;

public interface BidService {

	public List<BidForm> readAll();

	public Bid create(Bid bid);
	
	public Bid read(Integer bidId);
	
	public Bid update(Bid bid);
	
	public boolean delete(Integer bidId);
}

package swa.poseidon.services;

import java.util.List;

import javax.validation.Valid;

import swa.poseidon.form.BidForm;
import swa.poseidon.model.Bid;

public interface BidService {

	public List<BidForm> getAll();

	public Bid save(@Valid Bid bid);
}

package swa.poseidon.services;

import org.springframework.stereotype.Service;
import swa.poseidon.form.BidForm;
import swa.poseidon.model.Bid;

@Service
public class BidServiceImpl extends EntityCrudServiceImpl<Bid,BidForm> implements BidService 
{
}

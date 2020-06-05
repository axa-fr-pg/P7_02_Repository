package swa.poseidon.form;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import swa.poseidon.model.Bid;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
public class BidForm 
{
	Integer bidId;	
	String account;
	String type;
	BigDecimal bidQuantity;
	
	public BidForm(Bid b)
	{
		bidId=b.getBidId();
		account=b.getAccount();
		type=b.getType();
		bidQuantity=b.getBidQuantity();
	}
}

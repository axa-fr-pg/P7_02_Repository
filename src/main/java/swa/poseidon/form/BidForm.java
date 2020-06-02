package swa.poseidon.form;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import swa.poseidon.model.Bid;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

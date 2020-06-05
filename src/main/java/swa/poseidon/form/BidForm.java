package swa.poseidon.form;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import swa.poseidon.model.Bid;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
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
	
	public boolean matches(Bid b)
	{
		if (bidId != b.getBidId()) return false;
		if (!account.equals(b.getAccount())) return false;
		if (!type.equals(b.getType())) return false;
		if (bidQuantity.compareTo(b.getBidQuantity())!=0) return false;
		return true;
	}
}

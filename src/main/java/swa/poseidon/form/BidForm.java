package swa.poseidon.form;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import swa.poseidon.model.Bid;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class BidForm implements FormCore<Bid>
{
	Integer bidId;
	
	@NotBlank(message="account is mandatory")
	String account;
	
	@NotBlank(message="type is mandatory")
	String type;
	
	@NotNull(message="bidQuantity is mandatory")
	@Positive(message="bidQuantity must be positive")
	@Digits(integer=8, fraction=1)
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
		if (bidId.intValue() != b.getBidId().intValue()) return false;
		if (!account.equals(b.getAccount())) return false;
		if (!type.equals(b.getType())) return false;
		if (bidQuantity.compareTo(b.getBidQuantity())!=0) return false;
		return true;
	}

	@Override
	public Bid toEntity() 
	{
		return new Bid(this);
	}

	@Override
	public Integer id() {
		return bidId;
	}
}

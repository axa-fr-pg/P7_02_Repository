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
import swa.poseidon.model.Trade;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class TradeForm implements FormCore<Trade>
{
	Integer tradeId;

	@NotBlank(message="account is mandatory")
	String account;
	
	@NotBlank(message="type is mandatory")
	String type;
	
	@NotNull(message="buyQuantity is mandatory")
	@Positive(message="buyQuantity must be positive")
	@Digits(integer=8, fraction=1, message="buyQuantity must have format 8.1")
	BigDecimal buyQuantity;
	
	public TradeForm(Trade t)
	{
		tradeId=t.getTradeId();
		account=t.getAccount();
		type=t.getType();
		buyQuantity=t.getBuyQuantity();
	}

	@Override
	public Trade toEntity() 
	{
		return new Trade(this);
	}

	@Override
	public Integer id() {
		return tradeId;
	}

	@Override
	public boolean matches(Trade t) {
		if (tradeId.intValue() != t.getTradeId().intValue()) return false;
		if (!account.equals(t.getAccount())) return false;
		if (!type.equals(t.getType())) return false;
		if (buyQuantity.compareTo(t.getBuyQuantity())!=0) return false;
		return true;
	}
}

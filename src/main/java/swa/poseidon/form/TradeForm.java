package swa.poseidon.form;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import swa.poseidon.model.Trade;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
public class TradeForm 
{
	Integer tradeId;
	String account;
	String type;
	BigDecimal buyQuantity;
	
	public TradeForm(Trade t)
	{
		tradeId=t.getTradeId();
		account=t.getAccount();
		type=t.getType();
		buyQuantity=t.getBuyQuantity();
	}
}

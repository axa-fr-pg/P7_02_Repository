package swa.poseidon.form;

import java.math.BigDecimal;

import swa.poseidon.model.Trade;

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

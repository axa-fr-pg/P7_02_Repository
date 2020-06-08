package swa.poseidon.services;

import org.springframework.stereotype.Service;

import swa.poseidon.form.TradeForm;
import swa.poseidon.model.Trade;

@Service
public class TradeServiceImpl extends EntityServiceImpl<Trade,TradeForm> implements TradeService 
{
}

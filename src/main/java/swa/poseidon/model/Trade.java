package swa.poseidon.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import swa.poseidon.form.TradeForm;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class Trade implements EntityCore<TradeForm>
{
 	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer tradeId;
	
	@Column(length = 30)
	String account;
	
	@NotBlank
	@Column(length = 30)
	String type;
	
	@Column(columnDefinition = "DECIMAL(8,1)")
	BigDecimal buyQuantity;
	
	@Column(columnDefinition = "DECIMAL(8,1)")
	BigDecimal sellQuantity;
	
	@Column(columnDefinition = "DECIMAL(8,2)")
	BigDecimal buyPrice;
	
	@Column(columnDefinition = "DECIMAL(8,2)")
	BigDecimal sellPrice;
	
	@Column(length = 125)
	String benchmark;
	
	Timestamp tradeDate;
	
	@Column(length = 125)
	String security;
	
	@Column(columnDefinition = "TINYINT")
	Integer status;
	
	@Column(length = 125)
	String trader;
	
	@Column(length = 125)
	String book;
	
	@Column(length = 125)
	String creationName;

	@CreationTimestamp
	@Column(columnDefinition = "TIMESTAMP")
	Date creationDate;
	
	@Column(length = 125)	
	String revisionName;
	
	@UpdateTimestamp
	@Column(columnDefinition = "TIMESTAMP")
	Date revisionDate;
	
	@Column(length = 125)
	String dealName;

	@Column(length = 125)
	String dealType;
	
	Integer sourceListId;

	@Column(length = 125)
	String side;

	public Trade (String account, String type, BigDecimal buyQuantity)
	{
		// bidId set to 0
		this.account=account;
		this.type=type;
		this.buyQuantity=buyQuantity;
	}

	public Trade (TradeForm f)
	{
		this.tradeId=f.getTradeId();
		this.account=f.getAccount();
		this.type=f.getType();
		this.buyQuantity=f.getBuyQuantity();
	}

	@Override
	public void setId(Integer id) {
		tradeId=id;
	}

	@Override
	public TradeForm toForm() {
		return new TradeForm(this);
	}

	@Override
	public EntityCore<TradeForm> newValidTestEntityWithIdZero(int index) {
		return (EntityCore<TradeForm>) new Trade("account"+index, "type"+index, new BigDecimal(index*11.0));
	}

	@Override
	public EntityCore<TradeForm> newValidTestEntityWithGivenId(int index) {
		EntityCore<TradeForm> ec = newValidTestEntityWithIdZero(index);
		ec.setId(index);
		return ec;
	}

	@Override
	public EntityCore<TradeForm> newInvalidTestEntity() {
		return (EntityCore<TradeForm>) new Trade("", "", new BigDecimal(123456789.12));
	}
}

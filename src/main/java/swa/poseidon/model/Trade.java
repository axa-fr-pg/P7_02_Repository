package swa.poseidon.model;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
	
	@NotBlank
	@Column(length = 30)
	String account;
	
	@NotBlank
	@Column(length = 30)
	String type;
	
	@NotNull
	@Positive
	@Digits(integer=8, fraction=1)
	@Column(columnDefinition = "DECIMAL(8,1)")
	BigDecimal buyQuantity;
	
	@Digits(integer=8, fraction=1)
	@Column(columnDefinition = "DECIMAL(8,1)")
	BigDecimal sellQuantity;
	
	@Digits(integer=8, fraction=2)
	@Column(columnDefinition = "DECIMAL(8,2)")
	BigDecimal buyPrice;
	
	@Digits(integer=8, fraction=2)
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
}

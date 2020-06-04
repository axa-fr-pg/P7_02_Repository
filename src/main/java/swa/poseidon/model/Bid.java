package swa.poseidon.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import swa.poseidon.form.BidForm;
import swa.poseidon.services.EntityModelService;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bid implements EntityModelService
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer bidId;
	
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
	BigDecimal bidQuantity;
	
	@Digits(integer=8, fraction=1)
	@Column(columnDefinition = "DECIMAL(8,1)")
	BigDecimal askQuantity;
	
	@Digits(integer=8, fraction=2)
	@Column(columnDefinition = "DECIMAL(8,2)")
	BigDecimal bidAmount;
	
	@Digits(integer=8, fraction=2)
	@Column(columnDefinition = "DECIMAL(8,2)")
	BigDecimal askAmount;
	
	@Column(length = 125)
	String benchmark;

	/*
	 * This date is of no use (we already have creation & revision dates
	 *
	 *	@Column(columnDefinition = "TIMESTAMP")
	 *	Date bidDate;
	 *
	 */
	
	@Column(length = 125)
	String comment;

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

	public Bid (String account, String type, BigDecimal bidQuantity)
	{
		// bidId set to 0
		this.account=account;
		this.type=type;
		this.bidQuantity=bidQuantity;
	}

	@Override
	public void setId(Integer id) {
		setBidId(id);
	}

	@Override
	public Object newForm() {
		return new BidForm(this);
	}
}

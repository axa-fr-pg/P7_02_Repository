package swa.poseidon.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

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
public class Bid {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer bidId;
	
	@NotBlank
	String account;
	
	@NotBlank
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
	
	String benchmark;

	/*
	 * This date is of no use (we already have creation & revision dates
	 *
	 *	@Column(columnDefinition = "TIMESTAMP")
	 *	Date bidDate;
	 *
	 */
	
	String comment;
	String security;
	
	@Column(columnDefinition = "TINYINT")
	Integer status;
	
	String trader;
	String book;
	String creationName;
	
	@CreationTimestamp
	@Column(columnDefinition = "TIMESTAMP")
	Date creationDate;
	
	String revisionName;
	
	@UpdateTimestamp
	@Column(columnDefinition = "TIMESTAMP")
	Date revisionDate;
	
	String dealName;
	String dealType;
	Integer sourceListId;
	String side;

	public Bid (String account, String type, BigDecimal bidQuantity)
	{
		// bidId set to 0
		this.account=account;
		this.type=type;
		this.bidQuantity=bidQuantity;
	}
}

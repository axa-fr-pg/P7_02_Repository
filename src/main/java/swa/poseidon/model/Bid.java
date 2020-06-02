package swa.poseidon.model;

import org.springframework.beans.factory.annotation.Required;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bid {
    // TODO: Map columns in data table BIDLIST with corresponding java fields
	@Id	
	Integer bidId;
	
	String account;
	String type;
	Integer bidQuantity;
	Integer askQuantity;
	
	@Column(columnDefinition = "DECIMAL(8,2)")
	BigDecimal bidAmount;
	
	@Column(columnDefinition = "DECIMAL(8,2)")
	BigDecimal askAmount;
	
	String benchmark;

	@Temporal(TemporalType.TIMESTAMP)
	Date bidDate;
	
	String comment;
	String security;
	
	@Column(columnDefinition = "TINYINT")
	Integer status;
	
	String trader;
	String book;
	String creationName;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date creationDate;
	
	String revisionName;
	
	@Temporal(TemporalType.TIMESTAMP)
	Date revisionDate;
	
	String dealName;
	String dealType;
	Integer sourceListId;
	String side;	
}

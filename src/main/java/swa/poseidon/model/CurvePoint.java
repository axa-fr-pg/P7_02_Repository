package swa.poseidon.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CurvePoint {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer curvePointId;
	
	@NotNull
	@Positive
	@Column(columnDefinition = "TINYINT")
	Integer curveId;
	
	@Digits(integer=8, fraction=1)
	@Column(columnDefinition = "DECIMAL(8,1)")
	BigDecimal term;
	
	@Digits(integer=8, fraction=1)
	@Column(columnDefinition = "DECIMAL(8,1)")
	BigDecimal value;

	@UpdateTimestamp
	@Column(columnDefinition = "TIMESTAMP")
	Date revisionDate;
	
	@CreationTimestamp
	@Column(columnDefinition = "TIMESTAMP")
	Date creationDate;
	
	public CurvePoint (Integer curveId, BigDecimal term, BigDecimal value)
	{
		// curvePointId set to 0
		this.curveId=curveId;
		this.term=term;
		this.value=value;
	}
}

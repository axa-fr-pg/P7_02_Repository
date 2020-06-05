package swa.poseidon.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import swa.poseidon.form.CurvePointForm;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class CurvePoint implements EntityCore<CurvePointForm>
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer curvePointId;
	
	@NotNull
	@Positive
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

	@Override
	public void setId(Integer id) 
	{
		curvePointId=id;
	}

	@Override
	public CurvePointForm newForm() 
	{
		return new CurvePointForm(this);
	}
}

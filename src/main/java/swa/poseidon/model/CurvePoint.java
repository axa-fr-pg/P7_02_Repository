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
	
	Integer curveId;
	
	@Column(columnDefinition = "DECIMAL(8,1)")
	BigDecimal term;
	
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

	public CurvePoint (CurvePointForm f)
	{
		this.curvePointId=f.getCurvePointId();
		this.curveId=f.getCurveId();
		this.term=f.getTerm();
		this.value=f.getValue();
	}

	@Override
	public void setId(Integer id) 
	{
		curvePointId=id;
	}

	@Override
	public CurvePointForm toForm() 
	{
		return new CurvePointForm(this);
	}

	@Override
	public EntityCore<CurvePointForm> newValidTestEntityWithIdZero(int index) 
	{
		return (EntityCore<CurvePointForm>) new CurvePoint(index, new BigDecimal(11.0 * index), new BigDecimal(12.0 * index));
	}

	@Override
	public EntityCore<CurvePointForm> newValidTestEntityWithGivenId(int index) 
	{
		EntityCore<CurvePointForm> ec = newValidTestEntityWithIdZero(index);
		ec.setId(index);
		return ec;
	}

	@Override
	public EntityCore<CurvePointForm> newInvalidTestEntity() 
	{
		return (EntityCore<CurvePointForm>) new CurvePoint(0, new BigDecimal(123456789.11), new BigDecimal(123456789.11));
	}
}

package swa.poseidon.form;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import swa.poseidon.model.CurvePoint;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
public class CurvePointForm 
{
	Integer curvePointId;
	Integer curveId;
	BigDecimal term;
	BigDecimal value;

	public CurvePointForm(CurvePoint cv)
	{
		curvePointId=cv.getCurvePointId();
		curveId=cv.getCurveId();
		term=cv.getTerm();
		value=cv.getValue();
	}
}

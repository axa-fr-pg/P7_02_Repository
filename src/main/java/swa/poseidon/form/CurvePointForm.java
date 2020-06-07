package swa.poseidon.form;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import swa.poseidon.model.CurvePoint;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class CurvePointForm implements FormCore<CurvePoint>
{
	Integer curvePointId;
	
	@NotNull
	@Positive
	Integer curveId;
	
	@Digits(integer=8, fraction=1, message="term must have format 8.1")
	BigDecimal term;

	@Digits(integer=8, fraction=1, message="term must have format 8.1")
	BigDecimal value;

	public CurvePointForm(CurvePoint cp)
	{
		curvePointId=cp.getCurvePointId();
		curveId=cp.getCurveId();
		term=cp.getTerm();
		value=cp.getValue();
	}

	@Override
	public CurvePoint toEntity() {
		return new CurvePoint(this);
	}

	@Override
	public Integer id() {
		return curvePointId;
	}

	@Override
	public boolean matches(CurvePoint e) {
		if (curvePointId.intValue() != e.getCurvePointId().intValue()) return false;
		if (curveId.intValue() != e.getCurveId().intValue()) return false;
		if (term.compareTo(e.getTerm())!=0) return false;
		if (value.compareTo(e.getValue())!=0) return false;
		return true;
	}
}

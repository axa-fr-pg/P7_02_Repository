package swa.poseidon.form;

import java.math.BigDecimal;

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
	Integer curveId;
	BigDecimal term;
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
		if (!term.equals(e.getTerm())) return false;
		if (!value.equals(e.getValue())) return false;
		return true;
	}
}

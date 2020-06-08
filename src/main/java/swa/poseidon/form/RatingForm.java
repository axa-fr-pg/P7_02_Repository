package swa.poseidon.form;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import swa.poseidon.model.Rating;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class RatingForm extends Rating implements FormCore<Rating>
{
	public RatingForm(Rating r)
	{
		super(r);
	}

	@Override
	public Rating toEntity() {
		return new Rating(this);
	}

	@Override
	public Integer id() {
		return super.getRatingId();
	}

	@Override
	public boolean matches(Rating r) 
	{
		if (super.getRatingId().intValue() != r.getRatingId().intValue()) return false;
		if (!super.getMoodysRating().equals(r.getMoodysRating())) return false;
		if (!super.getStandPoorRating().equals(r.getStandPoorRating())) return false;
		if (!super.getFitchRating().equals(r.getFitchRating())) return false;
		if (super.getOrderNumber().intValue() != r.getOrderNumber().intValue()) return false;
		return true;
	}
}

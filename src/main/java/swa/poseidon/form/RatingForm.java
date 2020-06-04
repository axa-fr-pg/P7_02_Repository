package swa.poseidon.form;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import swa.poseidon.model.Rating;

@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingForm 
{
	Integer ratingId;
	String moodysRating;
	String standPoorRating;
	String fitchRating;
	Integer orderNumber;

	RatingForm(Rating r)
	{
		ratingId=r.getRatingId();
		moodysRating=r.getMoodysRating();
		standPoorRating=r.getStandPoorRating();
		fitchRating=r.getFitchRating();
		orderNumber=r.getOrderNumber();
	}
}

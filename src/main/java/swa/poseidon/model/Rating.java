package swa.poseidon.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import swa.poseidon.form.RatingForm;

@Entity
@FieldDefaults(level=AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class Rating implements EntityCore<RatingForm>
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer ratingId;

	@NotBlank(message="moodysRating is mandatory")
	@Column(length = 125)
	String moodysRating;

	@NotBlank(message="standPoorRating is mandatory")
	@Column(length = 125)
	String standPoorRating;

	@NotBlank(message="fitchRating is mandatory")
	@Column(length = 125)
	String fitchRating;

	@NotNull(message="orderNumber is mandatory")
	@Positive(message="orderNumber must be positive")
	Integer orderNumber;
	
	public Rating(String moodysRating, String standPoorRating, String fitchRating, Integer orderNumber)
	{
		// ratingId set to 0
		this.moodysRating=moodysRating;
		this.standPoorRating=standPoorRating;
		this.fitchRating=fitchRating;
		this.orderNumber=orderNumber;
	}

	public Rating(Rating r)
	{
		this.ratingId=r.getRatingId();
		this.moodysRating=r.getMoodysRating();
		this.standPoorRating=r.getStandPoorRating();
		this.fitchRating=r.getFitchRating();
		this.orderNumber=r.getOrderNumber();
	}

	@Override
	public void setId(Integer id) {
		ratingId=id;
	}

	@Override
	public RatingForm toForm() {
		return new RatingForm(this);
	}

	@Override
	public EntityCore<RatingForm> newValidTestEntityWithIdZero(int index) 
	{
		return (EntityCore<RatingForm>) new Rating("moodysRating"+index, "standPoorRating"+index, "fitchRating"+index, index*11);
	}

	@Override
	public EntityCore<RatingForm> newValidTestEntityWithGivenId(int index) 
	{
		EntityCore<RatingForm> ec = newValidTestEntityWithIdZero(index);
		ec.setId(index);
		return ec;
	}

	@Override
	public EntityCore<RatingForm> newInvalidTestEntity() {
		return (EntityCore<RatingForm>) new Rating("", "", "", -1);
	}
}

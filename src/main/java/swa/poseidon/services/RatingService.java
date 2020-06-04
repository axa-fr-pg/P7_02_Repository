package swa.poseidon.services;

import swa.poseidon.model.Rating;

public interface RatingService 
{
	public Rating create(Rating rating);
	
	public Rating read(Integer ratingId);
	
	public Rating update(Rating rating);
	
	public boolean delete(Integer ratingId);
}

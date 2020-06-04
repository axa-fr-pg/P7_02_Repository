package swa.poseidon.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import swa.poseidon.model.Rating;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {

	@Override
	public Rating create(Rating rating) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rating read(Integer ratingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rating update(Rating rating) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Integer ratingId) {
		// TODO Auto-generated method stub
		return false;
	}

}

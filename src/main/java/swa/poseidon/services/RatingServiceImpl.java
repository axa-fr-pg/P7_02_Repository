package swa.poseidon.services;

import org.springframework.stereotype.Service;
import swa.poseidon.form.RatingForm;
import swa.poseidon.model.Rating;

@Service
public class RatingServiceImpl extends EntityServiceImpl<Rating,RatingForm> implements RatingService 
{
}

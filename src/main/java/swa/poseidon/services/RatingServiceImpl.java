package swa.poseidon.services;

import org.springframework.stereotype.Service;
import swa.poseidon.form.RatingForm;
import swa.poseidon.model.Rating;

@Service
public class RatingServiceImpl extends EntityCrudServiceImpl<Rating,RatingForm> implements RatingService 
{
}

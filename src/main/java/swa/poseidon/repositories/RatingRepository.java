package swa.poseidon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import swa.poseidon.model.Rating;

@Transactional
public interface RatingRepository extends JpaRepository<Rating, Integer> {

}

package swa.poseidon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import swa.poseidon.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

}

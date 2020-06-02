package swa.poseidon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import swa.poseidon.model.CurvePoint;


public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}

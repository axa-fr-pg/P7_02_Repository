package swa.poseidon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import swa.poseidon.domain.CurvePoint;


public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}

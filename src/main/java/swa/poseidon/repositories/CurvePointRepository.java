package swa.poseidon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import swa.poseidon.model.CurvePoint;

@Transactional
public interface CurvePointRepository extends JpaRepository<CurvePoint, Integer> {

}

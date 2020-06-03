package swa.poseidon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import swa.poseidon.model.Bid;


@Transactional
public interface BidRepository extends JpaRepository<Bid, Integer> {

}

package swa.poseidon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import swa.poseidon.model.Bid;


public interface BidRepository extends JpaRepository<Bid, Integer> {

}

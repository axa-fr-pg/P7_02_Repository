package swa.poseidon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import swa.poseidon.domain.Trade;


public interface TradeRepository extends JpaRepository<Trade, Integer> {
}

package swa.poseidon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import swa.poseidon.model.Trade;

@Transactional
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}

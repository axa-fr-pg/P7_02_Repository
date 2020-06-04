package swa.poseidon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import swa.poseidon.model.Rule;

@Transactional
public interface RuleRepository extends JpaRepository<Rule, Integer> 
{
}

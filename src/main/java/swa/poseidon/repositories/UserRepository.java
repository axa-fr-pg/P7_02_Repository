package swa.poseidon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import swa.poseidon.model.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> 
{
    User findByUsername(String username);
}

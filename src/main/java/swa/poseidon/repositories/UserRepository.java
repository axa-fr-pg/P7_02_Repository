package swa.poseidon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import swa.poseidon.model.User;

@Transactional
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> 
{
    @Query(value = "select * from users where userName = :userName", nativeQuery = true)
    User findByUserName(@Param("userName") String userName);
}

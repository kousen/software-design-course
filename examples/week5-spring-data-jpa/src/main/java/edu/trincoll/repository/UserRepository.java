package edu.trincoll.repository;

import edu.trincoll.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Query method using naming convention
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    // Custom JPQL query with JOIN FETCH to avoid N+1 problem
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.tasks WHERE u.id = :id")
    Optional<User> findByIdWithTasks(@Param("id") Long id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.tasks WHERE u.username = :username")
    Optional<User> findByUsernameWithTasks(@Param("username") String username);
}
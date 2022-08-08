package ru.schoolservice.arm.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.User;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles")
    List<User> getAllBy();

    @Query("SELECT u FROM User u WHERE u.email = LOWER(:email)")
    Optional<User> findByEmailIgnoreCase(String email);

    List<User> findByLastNameContainingIgnoreCase(String lastName);

    @EntityGraph(attributePaths = {"roles"})
    List<User> findAll();
}

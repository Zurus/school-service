package ru.schoolservice.arm.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.schoolservice.arm.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles")
    List<User> getAllBy();

//    @EntityGraph(attributePaths = {"roles"})
//    List<User> findAll();
}

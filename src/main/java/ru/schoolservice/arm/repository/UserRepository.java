package ru.schoolservice.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findById(Integer id);

    void deleteAllByIdNotIn(Set<Integer> ids);

    List<User> findAllByTimurId(Integer timurId);

    void deleteAllByIdIn(Set<Integer> ids);

    void deleteAllByTimurId(Integer id);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.caches WHERE u.timurId = :timurId")
    List<User> findAllByTimurIdWithCaches(@Param("timurId") Integer timurId);

//    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles")
//    List<User> getAllBy();
//
//    @Query("SELECT u FROM User u WHERE u.email = LOWER(:email)")
//    Optional<User> findByEmailIgnoreCase(String email);
//
//    List<User> findByLastNameContainingIgnoreCase(String lastName);

//    @EntityGraph(attributePaths = {"roles"})
//    List<User> findAll();
}

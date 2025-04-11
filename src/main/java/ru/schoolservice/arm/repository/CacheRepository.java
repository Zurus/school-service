package ru.schoolservice.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.Cache;

import java.util.Optional;

@Transactional(readOnly = true)
public interface CacheRepository extends JpaRepository<Cache, Integer> {

//    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles")
//    List<User> getAllBy();
//
//    @Query("SELECT u FROM User u WHERE u.email = LOWER(:email)")
//    Optional<User> findByEmailIgnoreCase(String email);
//
//    List<User> findByLastNameContainingIgnoreCase(String lastName);

//    @EntityGraph(attributePaths = {"roles"})
//    List<User> findAll();


    //@Query("SELECT c FROM Cache c JOIN FETCH c.user WHERE c.id = :id")
//    @EntityGraph(value = "Cache.withUser", attributePaths = {"user"}, type = EntityGraph.EntityGraphType.FETCH)
    Optional<Cache> findById(Integer id);
}

package ru.secure_environment.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.secure_environment.arm.model.User;

import java.util.Optional;

import static ru.secure_environment.arm.util.validation.ValidationUtil.checkModification;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

//    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles")
//    List<User> getAllBy();

    @Query("SELECT u FROM User u WHERE u.email = LOWER(:email)")
//    @Cacheable("users")
    Optional<User> findByEmailIgnoreCase(String email);


    @Query("SELECT u FROM User u WHERE u.cardId = :cardId")
    Optional<User> findByCardId(String cardId);

//    @EntityGraph(attributePaths = {"roles"})
//    List<User> findAll();

//    @Override
//    @Transactional
//    @CachePut(value="users", key="#user.email")
//    User save(User user);
//
//    @Override
//    @Transactional
//    @CachePut(value="users", key="#user.email")
//    void delete(User user);
//
//    @Override
//    @Transactional
//    @CacheEvict(value = "users", allEntries = true)
//    void deleteById(Integer integer);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(int id);


    default void deleteExisted(int id) {
        checkModification(delete(id), id);
    }
}

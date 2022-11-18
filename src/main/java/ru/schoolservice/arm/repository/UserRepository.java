package ru.schoolservice.arm.repository;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.User;

import java.util.Optional;

//@Transactional(readOnly = true)
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //@Cacheable("users")
    Optional<User> findUserById(Integer id);

    @Override
    @Modifying
    @Transactional
    @CachePut(value = "users", key = "#user.id")
    User save(User user);

    @Override
    @Modifying
    @Transactional
    @CachePut(value="users", key = "user.id")
    void delete(User user);
}

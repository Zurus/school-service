package ru.schoolservice.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.schoolservice.arm.model.User;

//@Transactional(readOnly = true)
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}

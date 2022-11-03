package ru.schoolservice.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.User;

import java.util.List;
import java.util.Optional;

//@Transactional(readOnly = true)
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}

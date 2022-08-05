package ru.schoolservice.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.schoolservice.arm.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}

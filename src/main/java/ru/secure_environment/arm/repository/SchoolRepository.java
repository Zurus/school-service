package ru.secure_environment.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.secure_environment.arm.model.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, String> {
}

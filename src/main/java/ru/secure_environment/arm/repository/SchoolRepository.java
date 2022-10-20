package ru.secure_environment.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.secure_environment.arm.model.Events;
import ru.secure_environment.arm.model.School;

public interface SchoolRepository extends JpaRepository<School, String> {


}

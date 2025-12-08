package ru.schoolservice.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.schoolservice.arm.model.CheckPassportProcessesEntity;

@Repository
public interface CheckPassportProcessesRepository extends JpaRepository<CheckPassportProcessesEntity, Long> {
}

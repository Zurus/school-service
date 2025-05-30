package ru.schoolservice.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.Insurance;

@Transactional(readOnly = true)
@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Integer> {
}

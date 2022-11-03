package ru.schoolservice.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.schoolservice.arm.model.User;
import ru.schoolservice.arm.model.Vehicle;

//@Transactional(readOnly = true)
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
}

package ru.secure_environment.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.secure_environment.arm.model.Classes;
import ru.secure_environment.arm.model.School;

import java.util.Optional;

public interface SchoolClassRepository extends JpaRepository<Classes, Integer> {

    @Query("SELECT cl FROM Classes cl WHERE cl.name = :name AND cl.school = (SELECT sch FROM School sch WHERE sch.id = :id)")
    @Transactional
    Optional<Classes> findClassesByIdAndName(String id, String name);
}

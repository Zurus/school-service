package ru.secure_environment.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.secure_environment.arm.model.UnknownEvent;

@Repository
@Transactional(readOnly = true)
public interface UnknownEventRepository extends JpaRepository<UnknownEvent, Integer> {
}

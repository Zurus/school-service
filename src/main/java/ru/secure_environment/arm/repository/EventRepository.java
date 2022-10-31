package ru.secure_environment.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.secure_environment.arm.model.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {
}

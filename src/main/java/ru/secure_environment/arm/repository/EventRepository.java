package ru.secure_environment.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.secure_environment.arm.model.Event;

import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    List<Event> getAllByEventTimeBefore(Date borderDate);

    @Modifying
    @Query("DELETE FROM Event ev where ev.id in :ids")
    void deleteEventsWithIds(List<Integer> ids);
}

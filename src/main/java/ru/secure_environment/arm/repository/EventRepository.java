package ru.secure_environment.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.secure_environment.arm.model.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("SELECT case when COUNT(ev)>0 then true else false end FROM Event ev WHERE ev.logId = :logId AND ev.card.cardId = :cardId")
    Boolean existsEventByCardIdAndLogId(String cardId, int logId);
}

package ru.secure_environment.arm.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.secure_environment.arm.repository.EventRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase
class EventServiceTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    @Test
    void clearEvents() throws Exception {
        final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = format.parse("9-10-2022");
        eventService.clearEvents(date);
        eventRepository.findAll().forEach(System.out::println);
    }
}
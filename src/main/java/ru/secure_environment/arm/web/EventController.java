package ru.secure_environment.arm.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.secure_environment.arm.dto.EventDto;
import ru.secure_environment.arm.model.Events;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.repository.EventRepository;
import ru.secure_environment.arm.repository.UserRepository;

@RestController
@RequestMapping(value = EventController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
@Tag(name = "Event Controller")
public class EventController {
    public static final String URL = "/api/event";

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Events> createWithLocation(@RequestBody EventDto event) {
        log.info("event {}", event);
//            log.info("event {}", event);
//            User user = userRepository.findByCardId(event.getUserKeyCard()).orElseThrow(
//                    () -> new UsernameNotFoundException(event.getEvent() + " not found")
//            );
//            event.setUser(user);
//            eventRepository.save(event);
//            return ResponseEntity.ok(event);
//
        return null;
    }
}

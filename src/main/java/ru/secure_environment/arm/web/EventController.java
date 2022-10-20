package ru.secure_environment.arm.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.secure_environment.arm.dto.EventLogsDto;
import ru.secure_environment.arm.dto.EventResultDto;
import ru.secure_environment.arm.services.EventService;

import java.util.List;

@RestController
@RequestMapping(value = EventController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
@Tag(name = "Event Controller")
public class EventController {
    public static final String URL = "/api/event";

    private final EventService eventService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventResultDto>> create(@RequestBody EventLogsDto events) {
        log.info("event {}", events);
        List<EventResultDto> list = eventService.saveEvent(events.getLogs());
        return ResponseEntity.ok(list);
    }
}

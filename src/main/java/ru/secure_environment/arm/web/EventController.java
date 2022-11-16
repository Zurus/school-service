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
import ru.secure_environment.arm.error.ServerWorkException;
import ru.secure_environment.arm.services.EventService;
import ru.secure_environment.arm.services.NotificatorService;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping(value = EventController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
@Tag(name = "Event Controller")
public class EventController {
    public static final String URL = "/api/event";

    private final EventService eventService;
    private final NotificatorService notificatorService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventResultDto> create(@RequestBody EventLogsDto events) {
        log.info("event {}", events);
        List<EventResultDto> resultDtoList = eventService.saveEvent(events.getLogs());
        EventResultDto resultDto = resultDtoList.stream().max(Comparator.comparingInt(EventResultDto::getConfirmedLogId)).orElseThrow(
                () -> new ServerWorkException("cannot save event list = " + events)
        );
        notificatorService.sendNotifications(resultDtoList);
        return ResponseEntity.ok(resultDto);
    }
}

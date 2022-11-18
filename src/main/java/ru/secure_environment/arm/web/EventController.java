package ru.secure_environment.arm.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.secure_environment.arm.dto.EventLogsDto;
import ru.secure_environment.arm.dto.EventResultListDto;
import ru.secure_environment.arm.error.InnerWorkException;
import ru.secure_environment.arm.services.EventService;
import ru.secure_environment.arm.services.NotificatorService;

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
    public ResponseEntity<EventResultListDto> create(@RequestBody EventLogsDto events) {
        log.info("event {}", events);
        if(CollectionUtils.isEmpty(events.getLogs())) {
            throw new InnerWorkException("incorrect events");
        }
        EventResultListDto resultDtoList = eventService.saveEvent(events.getLogs());
        notificatorService.sendNotifications(resultDtoList.getEventResultDtos());
        return ResponseEntity.ok(resultDtoList);
    }
}

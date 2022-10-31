package ru.secure_environment.arm.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.secure_environment.arm.dto.EventDto;
import ru.secure_environment.arm.dto.EventResultDto;
import ru.secure_environment.arm.mapping.EventMapper;
import ru.secure_environment.arm.model.Event;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.repository.EventRepository;
import ru.secure_environment.arm.repository.UserRepository;
import ru.secure_environment.arm.util.DtoUtil;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Transactional
    public EventResultDto saveEvent(List<EventDto> list) {
        log.info("save events {}", list);

        EventResultDto eventResultDto = new EventResultDto(Integer.MIN_VALUE);
        for (EventDto eventDto : list) {
            if (!DtoUtil.hasEmptyFields(eventDto)) {
                log.warn("empty fields {}", eventDto);
                continue;
            }
            Event event = eventMapper.toModel(eventDto);
            User user = userRepository.findByCardId(event.getUserKeyCard()).orElse(null);
            if (Objects.isNull(user)) {
                log.warn("User with keyCard={} not found!", event.getUserKeyCard());
                continue;
            }
            event.setUser(user);
            //event.setId(1);
            event = eventRepository.save(event);
            eventResultDto.confirmIfBigger(event.getLogId());
        }
        return eventResultDto;
    }
}

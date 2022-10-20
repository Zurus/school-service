package ru.secure_environment.arm.services;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.secure_environment.arm.dto.EventDto;
import ru.secure_environment.arm.dto.EventResultDto;
import ru.secure_environment.arm.mapping.EventMapper;
import ru.secure_environment.arm.model.Events;
import ru.secure_environment.arm.model.User;
import ru.secure_environment.arm.repository.EventRepository;
import ru.secure_environment.arm.repository.UserRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Transactional
    public List<EventResultDto> saveEvent(List<EventDto> list) {
        log.info("save events {}", list);

        List<EventResultDto> resultList = list.stream()
                .filter(eventDto ->
                        Objects.nonNull(eventDto.getDirection())
                                && Objects.nonNull(eventDto.getKeyHex())
                                && Objects.nonNull(eventDto.getLogId())
                                && Objects.nonNull(eventDto.getTime()))
                .map(event -> {
                    Events events = eventMapper.toModel(event);
                    User user = userRepository.findByCardId(events.getUserKeyCard()).orElse(null);
                    if (Objects.isNull(user)) {
                        log.warn("User with keyCard={} not found!", events.getUserKeyCard());
                        return null;
                    }
                    events.setUser(user);
                    events.setId(1);
                    events = eventRepository.save(events);

                    return new EventResultDto(events.getLogId());
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return resultList;
    }

}

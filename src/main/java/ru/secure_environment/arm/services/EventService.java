package ru.secure_environment.arm.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.secure_environment.arm.dto.EventDto;
import ru.secure_environment.arm.dto.EventResultDto;
import ru.secure_environment.arm.mapping.EventMapper;
import ru.secure_environment.arm.model.Card;
import ru.secure_environment.arm.model.Event;
import ru.secure_environment.arm.repository.CardRepository;
import ru.secure_environment.arm.repository.EventRepository;
import ru.secure_environment.arm.util.DtoUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final CardRepository cardRepository;
    private final EventMapper eventMapper;

    @Transactional
    public List<EventResultDto> saveEvent(List<EventDto> list) {
        log.info("save events {}", list);

        List<EventResultDto> resultList = new ArrayList<>();
        for (EventDto eventDto : list) {
            if (!DtoUtil.hasEmptyFields(eventDto)) {
                log.warn("empty fields {}", eventDto);
                continue;
            }
            Event event = eventMapper.toModel(eventDto);
            Card card = cardRepository
                    .findCardByCardId(eventDto.getKeyHex())
                    .orElse(null);

            if (Objects.isNull(card)) {
                log.warn("Card = {} not found!", eventDto.getKeyHex());
                continue;
            }

            if (eventRepository.existsEventByCardIdAndLogId(eventDto.getKeyHex(), eventDto.getLogId())) {
                log.warn("Event = {} already saved!", eventDto.getKeyHex());
                continue;
            }
            event.setCard(card);
            event = eventRepository.save(event);
            resultList.add(new EventResultDto(event.getLogId()));
        }
        return resultList;
    }
}

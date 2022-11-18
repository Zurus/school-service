package ru.secure_environment.arm.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.secure_environment.arm.dto.EventDto;
import ru.secure_environment.arm.dto.EventResultListDto;
import ru.secure_environment.arm.mapping.EventMapper;
import ru.secure_environment.arm.model.AbstractEvent;
import ru.secure_environment.arm.model.Card;
import ru.secure_environment.arm.model.Event;
import ru.secure_environment.arm.model.UnknownEvent;
import ru.secure_environment.arm.repository.CardRepository;
import ru.secure_environment.arm.repository.EventRepository;
import ru.secure_environment.arm.repository.UnknownEventRepository;
import ru.secure_environment.arm.util.DtoUtil;
import ru.secure_environment.arm.util.validation.EventDtoRequirementComplianceEnum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final CardRepository cardRepository;
    private final EventMapper eventMapper;
    private final UnknownEventRepository unknownEventRepository;
    private final static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    @Transactional
    public void clearEvents(Date borderDate) {
        log.info("remove events before date = {}", formatter.format(borderDate));
        List<Integer> ids = eventRepository.getAllByEventTimeBefore(borderDate)
                .stream().map(Event::getId)
                .collect(Collectors.toList());
        eventRepository.deleteEventsWithIds(ids);
    }

    @Transactional
    public EventResultListDto saveEvent(List<EventDto> list) {
        log.info("save events {}", list);

        List<String> cardsHex = list.stream()
                .map(EventDto::getKeyHex)
                .collect(Collectors.toList());

        List<Card> cards = cardRepository.findAllCardByCardHex(cardsHex);

        Map<EventDtoRequirementComplianceEnum, List<EventDto>> eventDtoMap =
                list.stream()
                        .collect(Collectors.groupingBy(DtoUtil::getEventDtoRequirementComplianceEnum));

        List<Event> correctEventList = eventDtoMap.get(EventDtoRequirementComplianceEnum.CORRECT)
                .stream()
                .map(eventDto -> {
                    Event event = eventMapper.toModel(eventDto);
                    event.setCard(findCardByHex(cards, event));
                    return event;
                })
                .collect(Collectors.toList());

        List<UnknownEvent> incorrectEventList = eventDtoMap.get(EventDtoRequirementComplianceEnum.INCORRECT)
                .stream()
                .map(eventMapper::toUnknownEvent)
                .collect(Collectors.toList());

        eventRepository.saveAll(correctEventList);
        unknownEventRepository.saveAll(incorrectEventList);

        EventResultListDto eventResultListDto = new EventResultListDto();
        fillDataForNotifications(eventResultListDto, correctEventList);

        eventResultListDto.setConfirmedLogId(findMaxConfirmedId(
                new ArrayList<AbstractEvent>() {{
                    addAll(correctEventList);
                    addAll(incorrectEventList);
                }}
        ));

        return eventResultListDto;
    }

    private void fillDataForNotifications(EventResultListDto eventResultListDto, List<Event> eventList) {
        eventResultListDto.setEventResultDtos(
                eventList
                        .stream()
                        .map(eventMapper::toResultDto)
                        .collect(Collectors.toList())
        );
    }

    private Integer findMaxConfirmedId(List<? extends AbstractEvent> eventList) {
        return eventList.stream()
                .map(abstractEvent -> abstractEvent.getLogId())
                .max(Integer::compareTo)
                .get();
    }

    private Card findCardByHex(List<Card> cards, Event event) {
        return cards
                .stream()
                .filter(card -> card.getCardId().equals(event.getKeyHex()))
                .findFirst().get();
    }
}

package ru.practicum.exploreWithMe.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.enums.State;
import ru.practicum.exploreWithMe.exception.ForbiddenException;
import ru.practicum.exploreWithMe.exception.NotFoundException;
import ru.practicum.exploreWithMe.exception.ValidateException;
import ru.practicum.exploreWithMe.event.mapper.EventFullMapper;
import ru.practicum.exploreWithMe.event.model.Event;
import ru.practicum.exploreWithMe.event.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventPublicServiceImpl implements EventPublicService{

    private final EventRepository repository;

    @Override
    public List<EventShortDto> getEvents(String text, List<Long> categories, Boolean paid,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         Integer from, Integer size) {
        rangeStart = (rangeStart != null) ? rangeStart : LocalDateTime.now();
        rangeEnd = (rangeEnd != null) ? rangeEnd : LocalDateTime.now().plusYears(10);

        if (rangeStart.isAfter(rangeEnd)) {
            throw new ValidateException("The end date and time of the event cannot " +
                    "be earlier than the start date of the event.");
        }

        List<Event> events;

        if (categories != null) {
            events = repository.findByCategoryIdsAndText(text, categories);
        } else {
            events = repository.findByText(text);
        }

        List<EventShortDto> eventShortDtos = events.stream()
                .map(dtos -> EventFullMapper.eventToEventShortDto(dtos))
                .collect(toList());
            eventShortDtos = eventShortDtos.stream()
                    .sorted(Comparator.comparing(EventShortDto::getEventDate))
                    .skip(from)
                    .limit(size)
                    .collect(toList());
        return eventShortDtos;
}

    @Override
    public EventFullDto getEventById(Long id) {
        eventValidation(id);
        EventFullDto dto = EventFullMapper.eventToEventFullDto(repository.findById(id).get());
        if (!State.PUBLISHED.equals(dto.getState())) {
            throw new ForbiddenException("Event not published.");
        }
        log.info("events by id = " + id + " for an unregistered user");
        return dto;

    }

    private void eventValidation(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new NotFoundException("Events with id = " + id + " не найдено");
        }
    }
}

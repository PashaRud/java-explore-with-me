package ru.practicum.exploreWithMe.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.exploreWithMe.client.StatsClient;
import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.enums.State;
import ru.practicum.exploreWithMe.exception.ForbiddenException;
import ru.practicum.exploreWithMe.exception.NotFoundException;
import ru.practicum.exploreWithMe.event.mapper.EventFullMapper;
import ru.practicum.exploreWithMe.event.model.Event;
import ru.practicum.exploreWithMe.event.repository.EventRepository;
import ru.practicum.exploreWithMe.utils.FromSizeRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventPublicServiceImpl implements EventPublicService {

    private final EventRepository repository;
    private final StatsClient statsClient;

    @Override
    public List<EventShortDto> getEvents(String text, List<Long> categories, Boolean paid,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         Boolean onlyAvailable, String sort,
                                         Integer from, Integer size, HttpServletRequest request) {

        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now();
        saveEndpointHit(request);
        if (rangeStart != null) {
            start = rangeStart;
        }
        if (rangeEnd != null) {
            end = rangeEnd;
        }
        if (categories == null) {
            categories = new ArrayList<>();
        }
        Pageable pageable = FromSizeRequest.of(from, size);
        List<Event> events = repository.findEvents(text, categories, paid, start, end, onlyAvailable, pageable);

        if (Objects.equals(sort, "EVENT_DATE")) {
            events = events
                    .stream()
                    .sorted(Comparator.comparing(Event::getEventDate).reversed())
                    .collect(Collectors.toList());
        }
        return events.stream()
                .map(EventFullMapper::eventToEventShortDto)
                .collect(Collectors.toList());
    }
    @Override
    public EventFullDto getEventById(Long id, HttpServletRequest request) {
        eventValidation(id);
        saveEndpointHit(request);
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

    private void saveEndpointHit(HttpServletRequest request) {
        statsClient.addStats(request.getRequestURI(), request.getRemoteAddr());
    }

}

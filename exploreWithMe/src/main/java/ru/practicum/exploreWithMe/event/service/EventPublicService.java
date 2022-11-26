package ru.practicum.exploreWithMe.event.service;

import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.enums.EventSort;

import java.time.LocalDateTime;
import java.util.List;

public interface EventPublicService {

    List<EventShortDto> getEvents(String text, List<Long> categories, Boolean paid,
                                           LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                           Boolean onlyAvailable, EventSort sort, Integer from, Integer size);
    EventFullDto getEventById(Long eventId);

}

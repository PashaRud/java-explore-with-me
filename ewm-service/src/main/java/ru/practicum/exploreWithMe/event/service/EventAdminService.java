package ru.practicum.exploreWithMe.event.service;

import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.dto.UpdateEventRequest;
import ru.practicum.exploreWithMe.enums.State;

import java.util.List;

public interface EventAdminService {
    List<EventFullDto> getEvents(List<Long> users, List<State> states,
                                 List<Long> categories, String rangeStart,
                                 String rangeEnd, int from, int size);

    EventFullDto updateEvent(Long eventId, UpdateEventRequest updateEventRequest);

    EventFullDto publishEvent(Long eventId);

    EventFullDto rejectEvent(Long eventId);
}

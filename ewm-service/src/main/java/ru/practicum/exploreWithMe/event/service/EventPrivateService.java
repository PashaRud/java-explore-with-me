package ru.practicum.exploreWithMe.event.service;

import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.event.dto.NewEventDto;
import ru.practicum.exploreWithMe.event.dto.UpdateEventRequest;
import ru.practicum.exploreWithMe.request.dto.ParticipationRequestDto;

import java.util.List;

public interface EventPrivateService {
    List<EventShortDto> getEventsOfUser(Long userId, int from, int size);

    EventFullDto updateEvent(Long userId, UpdateEventRequest event);

    EventFullDto postEvent(Long userId, NewEventDto dto);

    EventFullDto getEvent(Long userId, Long eventId);

    EventFullDto cancelEvent(Long userId, Long eventId);

    List<ParticipationRequestDto> getRequests(Long userId, Long eventId);

    ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId);

    ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId);
}

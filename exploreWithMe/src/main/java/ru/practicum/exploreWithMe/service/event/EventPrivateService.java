package ru.practicum.exploreWithMe.service.event;

import ru.practicum.exploreWithMe.dto.event.EventFullDto;
import ru.practicum.exploreWithMe.dto.event.EventShortDto;
import ru.practicum.exploreWithMe.dto.event.NewEventDto;
import ru.practicum.exploreWithMe.dto.event.UpdateEventRequest;
import ru.practicum.exploreWithMe.dto.request.ParticipationRequestDto;

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

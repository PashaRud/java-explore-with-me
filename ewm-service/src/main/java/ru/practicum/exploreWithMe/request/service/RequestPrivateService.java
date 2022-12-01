package ru.practicum.exploreWithMe.request.service;

import ru.practicum.exploreWithMe.request.dto.ParticipationRequestDto;

import java.util.List;

public interface RequestPrivateService {

    List<ParticipationRequestDto> getRequestsByUserId(Long userId);

    ParticipationRequestDto createRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);
}

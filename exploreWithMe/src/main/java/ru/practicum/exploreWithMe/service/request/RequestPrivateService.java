package ru.practicum.exploreWithMe.service.request;

import ru.practicum.exploreWithMe.dto.request.ParticipationRequestDto;

import java.util.List;

public interface RequestPrivateService {

    List<ParticipationRequestDto> getRequestsByUserId(Long userId);

    ParticipationRequestDto createRequest(Long userId, Long eventId);

    ParticipationRequestDto cancelRequest(Long userId, Long requestId);
}

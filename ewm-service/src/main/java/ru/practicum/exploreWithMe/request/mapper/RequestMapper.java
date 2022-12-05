package ru.practicum.exploreWithMe.request.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.exploreWithMe.request.dto.ParticipationRequestDto;
import ru.practicum.exploreWithMe.request.model.Request;

@Component
public class RequestMapper {
    public static Request toRequest(ParticipationRequestDto requestDto) {
        return Request.builder()
                .id(requestDto.getId())
                .eventId(requestDto.getEvent())
                .requester(requestDto.getRequester())
                .status(requestDto.getStatus())
                .created(requestDto.getCreated())
                .build();
    }

    public static ParticipationRequestDto toRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .event(request.getEventId())
                .requester(request.getRequester())
                .status(request.getStatus())
                .created(request.getCreated())
                .build();
    }

}

package ru.practicum.exploreWithMe.mapper.request;

import org.springframework.stereotype.Component;
import ru.practicum.exploreWithMe.dto.request.ParticipationRequestDto;
import ru.practicum.exploreWithMe.model.Request;

@Component
public class RequestMapper {

    public static ParticipationRequestDto toRequestDto(Request request) {
        return new ParticipationRequestDto(request.getId(),
                request.getCreated().toString(),
                request.getEvent().getId(),
                request.getRequester().getId(),
                request.getStatus().toString());
    }
}

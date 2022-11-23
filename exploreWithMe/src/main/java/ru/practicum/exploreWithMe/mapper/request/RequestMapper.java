package ru.practicum.exploreWithMe.mapper.request;

import org.springframework.stereotype.Component;
import ru.practicum.exploreWithMe.dto.request.ParticipationRequestDto;
import ru.practicum.exploreWithMe.model.Request;

import java.util.ArrayList;
import java.util.List;

@Component
public class RequestMapper {

    public static ParticipationRequestDto toRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .created(request.getCreated())
                .eventId(request.getEvent().getId())
                .requesterId(request.getRequester().getId())
                .status(request.getStatus())
                .build();
    }



    public static List<ParticipationRequestDto> requestDtoList(Iterable<Request> requests) {
        List<ParticipationRequestDto> result = new ArrayList<>();
        for (Request request : requests) {
            result.add(toRequestDto(request));
        }
        return result;
    }
}

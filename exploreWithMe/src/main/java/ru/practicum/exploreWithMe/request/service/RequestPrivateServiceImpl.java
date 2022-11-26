package ru.practicum.exploreWithMe.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.request.dto.ParticipationRequestDto;
import ru.practicum.exploreWithMe.enums.State;
import ru.practicum.exploreWithMe.exception.NotFoundException;
import ru.practicum.exploreWithMe.exception.ValidateException;
import ru.practicum.exploreWithMe.request.mapper.RequestMapper;
import ru.practicum.exploreWithMe.event.model.Event;
import ru.practicum.exploreWithMe.request.model.Request;
import ru.practicum.exploreWithMe.event.repository.EventRepository;
import ru.practicum.exploreWithMe.request.repository.RequestRepository;
import ru.practicum.exploreWithMe.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RequestPrivateServiceImpl implements RequestPrivateService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    @Override
    public List<ParticipationRequestDto> getRequestsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format("User with id = " + userId + " not found"));
        }
        List<ParticipationRequestDto> dto = requestRepository.findByRequester(userId).stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
        log.info("User with  id = " + userId + " \n" +
                "provided a list of his requests", userId);
        return dto;
    }

    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {

        validateRequestSave(userId, eventId);
        ParticipationRequestDto requestDto = ParticipationRequestDto.builder()
                .requester(userId)
                .event(eventId)
                .status(State.PENDING)
                .created(LocalDateTime.now())
                .build();
        try {
            return RequestMapper.toRequestDto(requestRepository.save(RequestMapper.toRequest(requestDto)));
        } catch (RuntimeException e) {
            throw new ValidateException("Вы уже сделали запрос на участие в данном событии.");
        }
    }

    private void validateRequestSave(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new NotFoundException(String.format("События с id %d не существует", eventId)));
        if (!event.getRequestModeration()) {
            if (Objects.equals(event.getInitiator(), userId)) {
                throw new ValidateException("Организатор не может стать участником события.");
            }
            if (!event.getState().equals(State.PUBLISHED)) {
                throw new ValidateException("Событие еще не опубликовано.");
            }
            if ((event.getParticipantLimit() - event.getConfirmedRequests()) <= 0) {
                throw new ValidateException("Достигнут максимум участников.");
            }
        }
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
//        if (!requestRepository.existsById(userId)) {
//            throw new NotFoundException(String.format("Request for participation in the event with id = " + requestId +
//                    " not found"));
//        }
//        Request request = requestRepository.findById(requestId).get();
//
//        if (userId != request.getRequester().getId()) {
//            throw new ValidateException("Only the user who created the request can cancel it");
//        }
//        request.setStatus(Status.CANCELED);
//        ParticipationRequestDto dto = RequestMapper.toRequestDto(requestRepository.save(request));
//        log.info("Request for participation in the event with id = " + requestId +
//                " was canceled by the user with id = " + userId);
//        return dto;
//    }

                if (!requestRepository.existsById(userId)) {
            throw new NotFoundException(String.format("Request for participation in the event with id = " + requestId +
                    " not found"));
        }
        Request request = requestRepository.findById(requestId).get();

        if (userId != request.getRequester()) {
            throw new ValidateException("Only the user who created the request can cancel it");
        }
        request.setStatus(State.CANCELED);
        ParticipationRequestDto dto = RequestMapper.toRequestDto(requestRepository.save(request));
        log.info("Request for participation in the event with id = " + requestId +
                " was canceled by the user with id = " + userId);
        return dto;
    }
}

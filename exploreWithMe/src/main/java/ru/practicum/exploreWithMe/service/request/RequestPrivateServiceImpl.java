package ru.practicum.exploreWithMe.service.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.dto.request.ParticipationRequestDto;
import ru.practicum.exploreWithMe.enums.State;
import ru.practicum.exploreWithMe.enums.Status;
import ru.practicum.exploreWithMe.exception.NotFoundException;
import ru.practicum.exploreWithMe.exception.ValidateException;
import ru.practicum.exploreWithMe.mapper.request.RequestMapper;
import ru.practicum.exploreWithMe.model.Event;
import ru.practicum.exploreWithMe.model.Request;
import ru.practicum.exploreWithMe.repository.event.EventRepository;
import ru.practicum.exploreWithMe.repository.request.RequestRepository;
import ru.practicum.exploreWithMe.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
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
        List<ParticipationRequestDto> dto = requestRepository.findByRequesterId(userId).stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
        log.info("User with  id = " + userId + " \n" +
                "provided a list of his requests", userId);
        return dto;
    }

    @Override
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
//        if (!eventRepository.existsById(eventId)) {
//            throw new NotFoundException(String.format("Request for participation in the event with id = " + eventId + "" +
//                    " not found"));
//        }
//        if (!userRepository.existsById(userId)) {
//            throw new NotFoundException(String.format("User with id = " + userId + " not found"));
//        }

//        Event event = eventRepository.findById(eventId).get();
//
//        if (requestRepository.countByEventIdAndStatus(eventId, Status.CONFIRMED) != null) {
//            long eventConfirmedRequest = requestRepository.countByEventIdAndStatus(eventId, Status.CONFIRMED);
//            if (event.getParticipantLimit() != 0 && event.getParticipantLimit() == eventConfirmedRequest) {
//                throw new ValidateException(String.format("Limit of participation on event with id = " + eventId));
//            }
//        }
//        if (requestRepository.findByEventIdAndRequesterId(eventId, userId) != null) {
//            throw new ValidateException("The request for participation in the event cannot be added again");
//        }
//        if (userId == event.getInitiator().getId()) {
//            throw new ValidateException("The user who created the event cannot apply to participate in it");
//        }
//        if (!event.getState().equals(State.PUBLISHED)) {
//            throw new ValidateException("You can only apply for a published event");
//        }
//        Request request = new Request();
//        request.setRequester(userRepository.findById(userId).orElseThrow(() ->
//                new ValidateException("Wrong User id")));
//        request.setEvent(eventRepository.findById(eventId).orElseThrow(() ->
//                new ValidateException(("Wrong event id"))));
//        request.setCreated(LocalDateTime.now());
//        request.setStatus(Status.PENDING);
//        ParticipationRequestDto dto = RequestMapper.toRequestDto(requestRepository.save(request));
//        return dto;

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

        Request request = new Request();
        request.setRequester(userRepository.findById(userId).orElseThrow(() ->
                new ValidateException("Wrong User id")));
        request.setEvent(eventRepository.findById(eventId).orElseThrow(() ->
                new ValidateException(("Wrong event id"))));
        request.setCreated(LocalDateTime.now());
        request.setStatus(Status.PENDING);
        try {
            return RequestMapper.toRequestDto(requestRepository.save(request));
        } catch (RuntimeException e) {
            throw new ValidateException("Вы уже сделали запрос на участие в данном событии.");
        }
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        if (!requestRepository.existsById(userId)) {
            throw new NotFoundException(String.format("Request for participation in the event with id = " + requestId +
                    " not found"));
        }
        Request request = requestRepository.findById(requestId).get();

        if (userId != request.getRequester().getId()) {
            throw new ValidateException("Only the user who created the request can cancel it");
        }
        request.setStatus(Status.CANCELED);
        ParticipationRequestDto dto = RequestMapper.toRequestDto(requestRepository.save(request));
        log.info("Request for participation in the event with id = " + requestId +
                " was canceled by the user with id = " + userId);
        return dto;
    }
}

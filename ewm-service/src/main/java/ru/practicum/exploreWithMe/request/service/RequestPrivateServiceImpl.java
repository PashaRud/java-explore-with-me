package ru.practicum.exploreWithMe.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.enums.Status;
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
            throw new NotFoundException("User with id = " + userId + " not found");
        }
        List<ParticipationRequestDto> dto = requestRepository.findByRequester(userId).stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
        log.info("User with  id = " + userId + " \n" +
                "provided a list of his requests", userId);
        return dto;
    }

    @Override
    @Transactional
    public ParticipationRequestDto createRequest(Long userId, Long eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() ->
                        new NotFoundException("Event with id " + eventId + " does't exist"));
        if (!event.getRequestModeration()) {
            if (!event.getState().equals(State.PUBLISHED)) {
                throw new ValidateException("The event has not yet been published");
            }
            if ((event.getParticipantLimit() - event.getConfirmedRequests()) <= 0) {
                throw new ValidateException("The maximum number of participants has been reached");
            }
        }
        ParticipationRequestDto requestDto = ParticipationRequestDto.builder()
                .requester(userId)
                .event(eventId)
                .status(Status.PENDING)
                .created(LocalDateTime.now())
                .build();
        try {
            return RequestMapper.toRequestDto(requestRepository.save(RequestMapper.toRequest(requestDto)));
        } catch (DataIntegrityViolationException e) {
            throw new ValidateException("You have already made a request to participate in this event");
        }
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request for participation in the event with id = " + requestId +
                        " not found"));

        if (!userId.equals(request.getRequester())) {
            throw new ValidateException("Only the user who created the request can cancel it");
        }
        request.setStatus(Status.CANCELED);
        ParticipationRequestDto dto = RequestMapper.toRequestDto(requestRepository.save(request));
        log.info("Request for participation in the event with id = " + requestId +
                " was canceled by the user with id = " + userId);
        return dto;
    }
}

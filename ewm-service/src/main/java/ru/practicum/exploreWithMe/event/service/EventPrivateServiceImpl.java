package ru.practicum.exploreWithMe.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.enums.Status;
import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.event.dto.NewEventDto;
import ru.practicum.exploreWithMe.event.dto.UpdateEventRequest;
import ru.practicum.exploreWithMe.request.dto.ParticipationRequestDto;
import ru.practicum.exploreWithMe.enums.State;
import ru.practicum.exploreWithMe.exception.ForbiddenException;
import ru.practicum.exploreWithMe.exception.NotFoundException;
import ru.practicum.exploreWithMe.exception.ValidateException;
import ru.practicum.exploreWithMe.event.mapper.EventFullMapper;
import ru.practicum.exploreWithMe.request.mapper.RequestMapper;
import ru.practicum.exploreWithMe.event.model.Event;
import ru.practicum.exploreWithMe.request.model.Request;
import ru.practicum.exploreWithMe.category.repository.CategoryRepository;
import ru.practicum.exploreWithMe.event.repository.EventRepository;
import ru.practicum.exploreWithMe.request.repository.RequestRepository;
import ru.practicum.exploreWithMe.user.repository.UserRepository;
import ru.practicum.exploreWithMe.utils.FromSizeRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static ru.practicum.exploreWithMe.event.mapper.EventFullMapper.eventToEventShortDto;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EventPrivateServiceImpl implements EventPrivateService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");



    @Override
    public List<EventShortDto> getEventsOfUser(Long userId, int from, int size) {
        userValidation(userId);
        Pageable pageable = FromSizeRequest.of(from, size);

        List<EventShortDto> eventShortDtos = eventRepository.findByInitiatorId(userId, pageable).stream()
                .map(event -> eventToEventShortDto(event))
                .collect(toList());
        log.info("get Events Of User" + userId);
        return eventShortDtos;
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(Long userId, UpdateEventRequest updateEventRequest) {
        userValidation(userId);
        Event event = eventRepository.findById(updateEventRequest.getEventId())
                .orElseThrow(() -> new NotFoundException("Event with id=" + updateEventRequest.getEventId() +
                        " not found."));
        Long eventIniciatorId = event.getInitiator().getId();
        if (!userId.equals(eventIniciatorId)) {
            throw new ForbiddenException("This user is not the initiator");
        }
        if (updateEventRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventRequest.getAnnotation());
        }
        if (updateEventRequest.getCategoryId() != null) {
            event.setCategory(categoryRepository.findById(updateEventRequest.getCategoryId()).get());
        }
        if (updateEventRequest.getDescription() != null) {
            event.setDescription(updateEventRequest.getDescription());
        }

        if (updateEventRequest.getEventDate() != null) {
            event.setEventDate(LocalDateTime.parse(updateEventRequest.getEventDate(), formatter));
        }

        if (updateEventRequest.getPaid() != null) {
            event.setPaid(updateEventRequest.getPaid());
        }

        if (updateEventRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventRequest.getParticipantLimit());
        }

        if (updateEventRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventRequest.getRequestModeration());
        }

        if (updateEventRequest.getTitle() != null) {
            event.setTitle(updateEventRequest.getTitle());
        }
        if (event.getState().equals(State.CANCELED)) {
            event.setState(State.PENDING);
        }
        EventFullDto dto = EventFullMapper.eventToEventFullDto(eventRepository.save(event));
        log.info("update event: " + dto.getId());
        return dto;
    }

    @Override
    @Transactional
    public EventFullDto postEvent(Long userId, NewEventDto dto) {
        userValidation(userId);
        LocalDateTime now = LocalDateTime.now();
        if (LocalDateTime.parse(dto.getEventDate(), formatter).isBefore(now.plusHours(2))) {
            throw new ValidateException("Cannot be published closer than 2 hours before the start of the event");
        }

        Event event = new Event(dto.getId(),
                dto.getAnnotation(),
                dto.getDescription(),
                categoryRepository.findById(dto.getCategory()).get(),
                dto.getTitle(),
                now,
                now,
                LocalDateTime.parse(dto.getEventDate(), formatter),
                0,
                userRepository.findById(userId).get(),
                dto.getLocation().getLat(),
                dto.getLocation().getLon(),
                dto.isPaid(),
                dto.getParticipantLimit(),
                dto.getRequestModeration(),
                State.PENDING);
        eventRepository.save(event);
        EventFullDto eventFullDto = EventFullMapper.eventToEventFullDto(event);
        log.info("post event" + eventFullDto.getId());
        return eventFullDto;
    }

    @Override
    public EventFullDto getEvent(Long userId, Long eventId) {
        eventValidation(eventId);
        userValidation(userId);
        Event event = eventRepository.findById(eventId).get();
        Long iniciatorId = event.getInitiator().getId();
        initiatorValidation(userId, iniciatorId);
        EventFullDto eventFullDto = EventFullMapper.eventToEventFullDto(eventRepository.save(event));
        log.info("get Event: " + eventId);
        return eventFullDto;
    }

    @Override
    @Transactional
    public EventFullDto cancelEvent(Long userId, Long eventId) {
        eventValidation(eventId);
        userValidation(userId);
        Event event = eventRepository.findById(eventId).get();
        initiatorValidation(userId, event.getInitiator().getId());
        if (!event.getState().equals(State.PENDING)) {
            throw new ValidateException("Only PENDING state events can be cancelled");
        }
        event.setState(State.CANCELED);
        EventFullDto eventFullDto = EventFullMapper.eventToEventFullDto(eventRepository.save(event));
        log.info("cancel event" + eventId);
        return eventFullDto;
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId, Long eventId) {
        eventValidation(eventId);
        userValidation(userId);
        Event event = eventRepository.findById(eventId).get();
        initiatorValidation(userId, event.getInitiator().getId());

        List<ParticipationRequestDto> dtos = requestRepository.findByEventId(eventId).stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
        log.info("get Requests. UserId:" + userId + " .Event id: " + eventId);
        return dtos;
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId) {
        eventValidation(eventId);
        userValidation(userId);
        EventFullDto eventFullDto = EventFullMapper.eventToEventFullDto(eventRepository.findById(eventId).get());
        if (!eventFullDto.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("Can only be confirmed by the initiator");
        }
        if (!eventFullDto.getState().equals(State.PUBLISHED)) {
            throw new ForbiddenException("Cannot confirm a request to participate in an unpublished event");
        }
        if (eventFullDto.getConfirmedRequests() == eventFullDto.getParticipantLimit()) {
            throw new ForbiddenException("Participant limit reached");
        }
        Request request = requestRepository.findById(reqId).get();
        request.setStatus(Status.CONFIRMED);
        ParticipationRequestDto dto = RequestMapper.toRequestDto(requestRepository.save(request));
        log.info("confirm Requests. UserId:" + userId + " .Event id: " + eventId);
        return dto;
    }

    @Override
    @Transactional
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId) {
        eventValidation(eventId);
        userValidation(userId);

        EventFullDto eventFullDto = EventFullMapper.eventToEventFullDto(eventRepository.findById(eventId).get());
        if (!eventFullDto.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("Can only be confirmed by the initiator");
        }
        if (!eventFullDto.getState().equals(State.PUBLISHED)) {
            throw new ForbiddenException("Cannot confirm a request to participate in an unpublished event");
        }
        Request request = requestRepository.findById(reqId).get();
        request.setStatus(Status.REJECTED);
        ParticipationRequestDto dto = RequestMapper.toRequestDto(requestRepository.save(request));
        log.info("reject Requests. UserId:" + userId + " .Event id: " + eventId);
        return dto;
    }

    private void userValidation(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with id = " + id + " not found");
        }
    }

    private void eventValidation(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new NotFoundException("Event with id = " + id + " not found");
        }
    }

    private void initiatorValidation(Long userId, Long initiatorId) {
        if (!userId.equals(initiatorId)) {
            throw new ValidateException("Only the creator of the event can perform this action on it.");
        }
    }
}
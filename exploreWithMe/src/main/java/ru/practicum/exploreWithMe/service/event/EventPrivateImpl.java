package ru.practicum.exploreWithMe.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.dto.event.EventFullDto;
import ru.practicum.exploreWithMe.dto.event.EventShortDto;
import ru.practicum.exploreWithMe.dto.event.NewEventDto;
import ru.practicum.exploreWithMe.dto.event.UpdateEventRequest;
import ru.practicum.exploreWithMe.dto.request.ParticipationRequestDto;
import ru.practicum.exploreWithMe.enums.State;
import ru.practicum.exploreWithMe.enums.Status;
import ru.practicum.exploreWithMe.exception.ForbiddenException;
import ru.practicum.exploreWithMe.exception.NotFoundException;
import ru.practicum.exploreWithMe.exception.ValidateException;
import ru.practicum.exploreWithMe.mapper.event.EventFullMapper;
import ru.practicum.exploreWithMe.mapper.request.RequestMapper;
import ru.practicum.exploreWithMe.model.Event;
import ru.practicum.exploreWithMe.model.Request;
import ru.practicum.exploreWithMe.repository.event.EventRepository;
import ru.practicum.exploreWithMe.repository.user.UserRepository;
import ru.practicum.exploreWithMe.utils.FromSizeRequest;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventPrivateImpl implements EventPrivateService {

    private final EventRepository repository;
    private final CategoryRepository categoryRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventFullMapper mapper;


    @Override
    public List<EventShortDto> getEventsOfUser(Long userId, int from, int size) {
        userValidation(userId);
        Pageable pageable = FromSizeRequest.of(from, size);

        List<EventShortDto> eventShortDtos = repository.findByInitiatorId(userId, pageable).stream()
                .map(mapper::eventToEventShortDto)
                .collect(toList());
        return eventShortDtos;
    }

    @Override
    public EventFullDto updateEvent(Long userId, UpdateEventRequest updateEventRequest) {
        userValidation(userId);
        Event event = repository.findById(updateEventRequest.getEventId())
                .orElseThrow(() -> new NotFoundException("Event with id=" + updateEventRequest.getEventId() +
                        " not found."));

        if (!userId.equals(userRepository.findByEventId(updateEventRequest.getEventId()))) {
            throw new ForbiddenException("This user is not the initiator");
        }
        if (updateEventRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventRequest.getAnnotation());
        }
        if (updateEventRequest.getCategoryId() != null) {
            //Добавить категорию
        }
        if (updateEventRequest.getDescription() != null) {
            event.setDescription(updateEventRequest.getDescription());
        }

        if (updateEventRequest.getEventDate() != null) {
            event.setEventDate(updateEventRequest.getEventDate());
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
        EventFullDto dto = mapper.eventToEventFullDto(repository.save(event));

        return dto;
    }

    @Override
    public EventFullDto postEvent(Long userId, NewEventDto dto) {
        userValidation(userId);
        LocalDateTime now = LocalDateTime.now();
        if (dto.getEventDate().isBefore(now.plusHours(2))) {
            throw new ValidateException("Cannot be published closer than 2 hours before the start of the event");
        }

        Event event = new Event(0,
                dto.getAnnotation(),
                dto.getDescription(),


                //примерно так
                categoryRepository.findById(dto.getCategory().getId()),
                dto.getTitle(),
                now,
                null,
                dto.getEventDate(),
                dto.getInitiator(),
                dto.getLocation().getLat(),
                dto.getLocation().getLon(),
                dto.isPaid(),
                dto.getParticipantLimit(),
                dto.isRequestModeration(),
                State.PENDING);
        EventFullDto eventFullDto = mapper.eventToEventFullDto(repository.save(event));
        return eventFullDto;
    }

    @Override
    public EventFullDto getEvent(Long userId, Long eventId) {
        eventValidation(eventId);
        userValidation(userId);
        Event event = repository.findById(eventId).get();
        initiatorValidation(userId, event.getInitiator().getId());
        EventFullDto eventFullDto = mapper.eventToEventFullDto(repository.save(event));

        return eventFullDto;
    }

    @Override
    public EventFullDto cancelEvent(Long userId, Long eventId) {
        eventValidation(eventId);
        userValidation(userId);
        Event event = repository.findById(eventId).get();
        initiatorValidation(userId, event.getInitiator().getId());
        if (!event.getState().equals(State.PENDING)) {
            throw new ValidateException("Only PENDING state events can be cancelled");
        }
        event.setState(State.CANCELED);
        EventFullDto eventFullDto = mapper.eventToEventFullDto(repository.save(event));
        return eventFullDto;
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId, Long eventId) {
        eventValidation(eventId);
        userValidation(userId);
        Event event = repository.findById(eventId).get();
        initiatorValidation(userId, event.getInitiator().getId());


        //Примерный метод
        List<ParticipationRequestDto> dtos = requestRepository.findByEventId(eventId).stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
        return dtos;
    }

    @Override
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId) {
        eventValidation(eventId);
        userValidation(userId);
        EventFullDto eventFullDto = mapper.eventToEventFullDto(repository.findById(eventId).get());
        if (!eventFullDto.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("Can only be confirmed by the initiator");
        }
        if (!eventFullDto.getState().equals(State.PUBLISHED)) {
            throw new ForbiddenException("Cannot confirm a request to participate in an unpublished event");
        }
        if(eventFullDto.getConfirmedRequests() == eventFullDto.getParticipantLimit()) {
            throw new ForbiddenException("Participant limit reached");
        }

        //Примерный метод
        Request request = requestRepository.findById(reqId).get();
        request.setStatus(Status.CONFIRMED);
        ParticipationRequestDto dto = RequestMapper.toRequestDto(requestRepository.save(request));
        return dto;
    }

    @Override
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId) {
        eventValidation(eventId);
        userValidation(userId);

        EventFullDto eventFullDto = mapper.eventToEventFullDto(repository.findById(eventId).get());
        if (!eventFullDto.getInitiator().getId().equals(userId)) {
            throw new ForbiddenException("Can only be confirmed by the initiator");
        }
        if (!eventFullDto.getState().equals(State.PUBLISHED)) {
            throw new ForbiddenException("Cannot confirm a request to participate in an unpublished event");
        }


        //Примерный метод
        Request request = requestRepository.findById(reqId).get();
        request.setStatus(Status.REJECTED);
        ParticipationRequestDto dto = RequestMapper.toRequestDto(requestRepository.save(request));

        return dto;
    }

    private void userValidation(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(String.format("User with id = '%s' not found", id));
        }
    }

    private void eventValidation(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(String.format("Event with id = '%s' not found", id));
        }
    }

    private void initiatorValidation(long userId, long initiatorId) {
        if (userId != initiatorId) {
            throw new ValidateException("Only the creator of the event can perform this action on it.");
        }
    }
}

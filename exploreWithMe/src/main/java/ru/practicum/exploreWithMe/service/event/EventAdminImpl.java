package ru.practicum.exploreWithMe.service.event;

import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.dto.event.EventFullDto;
import ru.practicum.exploreWithMe.dto.event.UpdateEventRequest;
import ru.practicum.exploreWithMe.enums.State;
import ru.practicum.exploreWithMe.exception.NotFoundException;
import ru.practicum.exploreWithMe.exception.ValidateException;
import ru.practicum.exploreWithMe.mapper.event.EventFullMapper;
import ru.practicum.exploreWithMe.model.Event;
import ru.practicum.exploreWithMe.repository.event.EventRepository;
import ru.practicum.exploreWithMe.utils.FromSizeRequest;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventAdminImpl implements EventAdminService {

    private final EventRepository repository;
    private final EntityManager entityManager;
    private final EventFullMapper mapper;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional
    public List<EventFullDto> getEvents(List<Integer> users, List<State> states,
                                        List<Integer> categories, String rangeStart,
                                        String rangeEnd, int from, int size) {
        LocalDateTime start = null;
        LocalDateTime end = null;
        if (rangeStart != null) {
            try {
                start = LocalDateTime.parse(rangeStart, formatter);
            } catch (ValidateException e) {
                throw new ValidateException("Incorrect rangeStart value = " + rangeStart);
            }
        }
        if (rangeEnd != null) {
            try {
                end = LocalDateTime.parse(rangeEnd, formatter);
            } catch (ValidateException e) {
                throw new ValidateException("Incorrect rangeEnd value = " + rangeEnd);
            }
        }
        if (start.isAfter(end)) {
            throw new ValidateException("Ending event before it starts");
        }
        Session session = entityManager.unwrap(Session.class);
        Filter dateFilter = session.enableFilter("dateFilter");
        dateFilter.setParameter("rangeStart", rangeStart);
        dateFilter.setParameter("rangeEnd", rangeEnd);

        List<Event> events = new ArrayList<>();

        Pageable pageable = FromSizeRequest.of(from, size);

        if ((categories != null) && (users != null)) {
            events = repository.findByUsersAndCategoriesAndStates(users, categories, states, pageable);
        }

        if ((categories == null) && (users == null)) {
            events = repository.findByStates(states, pageable);
        }

        if ((categories != null) && (users == null)) {
            events = repository.findByCategoriesAndStates(categories, states, pageable);
        }

        if ((categories == null) && (users != null)) {
            events = repository.findByUsersAndStates(users, states, pageable);
        }

        session.disableFilter("dateFilter");

        List<EventFullDto> eventFullDtos = events.stream()
                .map(mapper::eventToEventFullDto)
                .collect(toList());

        return eventFullDtos;
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(Long eventId, UpdateEventRequest updateEventRequest) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " not found."));

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

        event = repository.save(event);

        return mapper.eventToEventFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto publishEvent(Long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " not found."));
        if (!event.getState().equals(State.PENDING)) {
            throw new ValidateException("State must be PENDING. Now - " + event.getState());
        }
        if (event.getEventDate().equals(LocalDateTime.now().plusHours(1))) {
            throw new ValidateException("Too late to update this event.");
        }
        event.setState(State.PUBLISHED);
        EventFullDto eventDto = mapper.eventToEventFullDto(repository.save(event));
        return eventDto;
    }

    @Override
    @Transactional
    public EventFullDto rejectEvent(Long eventId) {
        Event event = repository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " not found."));
        if (event.getState().equals(State.PUBLISHED)) {
            throw new ValidateException("Cannot be rejected");
        }
        event.setState(State.CANCELED);
        EventFullDto eventDto = mapper.eventToEventFullDto(repository.save(event));
        return eventDto;
    }
}


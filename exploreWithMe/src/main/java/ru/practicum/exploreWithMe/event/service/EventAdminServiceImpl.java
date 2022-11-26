package ru.practicum.exploreWithMe.event.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.dto.UpdateEventRequest;
import ru.practicum.exploreWithMe.enums.State;
import ru.practicum.exploreWithMe.exception.NotFoundException;
import ru.practicum.exploreWithMe.exception.ValidateException;
import ru.practicum.exploreWithMe.event.model.Event;
import ru.practicum.exploreWithMe.category.repository.CategoryRepository;
import ru.practicum.exploreWithMe.event.repository.EventRepository;
import ru.practicum.exploreWithMe.utils.FromSizeRequest;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static ru.practicum.exploreWithMe.event.mapper.EventFullMapper.eventToEventFullDto;

@Service
@Transactional
@RequiredArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final EntityManager entityManager;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional
    public List<EventFullDto> getEvents(List<Long> users, List<State> states,
                                        List<Long> categories, String rangeStart,
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

        start = (rangeStart != null) ? start : LocalDateTime.now();
        end = (rangeEnd != null) ? end : LocalDateTime.now().plusYears(300);

        if (start.isAfter(end)) {
            throw new ValidateException("Ending event before it starts");
        }



//        LocalDateTime start = LocalDateTime.parse(rangeStart, formatter);
//        LocalDateTime end = LocalDateTime.parse(rangeEnd, formatter);
//        start = (rangeStart != null) ? start : LocalDateTime.now();
//        end = (rangeEnd != null) ? end : LocalDateTime.now().plusYears(300);
//
//        if (start.isAfter(end)) {
//            throw new ValidateException("Дата и время окончаний события не может быть раньше даты начала событий!");
//        }

        if (states == null) {
            states = new ArrayList<>();
            states.add(State.PENDING);
            states.add(State.CANCELED);
            states.add(State.PUBLISHED);
        }

//        Session session = entityManager.unwrap(Session.class);
//        Filter dateFilter = session.enableFilter("dateFilter");
//        dateFilter.setParameter("rangeStart", rangeStart);
//        dateFilter.setParameter("rangeEnd", rangeEnd);

        List<Event> events = new ArrayList<>();

        Pageable pageable = FromSizeRequest.of(from, size);

        if ((categories != null) && (users != null)) {
            events = eventRepository.findByUsersAndCategoriesAndStates(users, categories, states, pageable);
        }

        if ((categories == null) && (users == null)) {
            events = eventRepository.findByStates(states, pageable);
        }

        if ((categories != null) && (users == null)) {
            events = eventRepository.findByCategoriesAndStates(categories, states, pageable);
        }

        if ((categories == null) && (users != null)) {
            events = eventRepository.findByUsersAndStates(users, states, pageable);
        }

//        session.disableFilter("dateFilter");

        List<EventFullDto> eventFullDtos = events.stream()
                .map(event -> eventToEventFullDto(event))
                .collect(toList());

        return eventFullDtos;
    }

    @Override
    @Transactional
    public EventFullDto updateEvent(Long eventId, UpdateEventRequest updateEventRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " not found."));

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

        event = eventRepository.save(event);

        return eventToEventFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto publishEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id = " + eventId + " not found."));
        if (!event.getState().equals(State.PENDING)) {
            throw new ValidateException("State must be PENDING. Now - " + event.getState());
        }
        if (event.getEventDate().equals(LocalDateTime.now().plusHours(1))) {
            throw new ValidateException("Too late to update this event.");
        }
        event.setState(State.PUBLISHED);
        EventFullDto eventDto = eventToEventFullDto(eventRepository.save(event));
        return eventDto;
    }

    @Override
    @Transactional
    public EventFullDto rejectEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event with id=" + eventId + " not found."));
        if (event.getState().equals(State.PUBLISHED)) {
            throw new ValidateException("Cannot be rejected");
        }
        event.setState(State.CANCELED);
        EventFullDto eventDto = eventToEventFullDto(eventRepository.save(event));
        return eventDto;
    }
}


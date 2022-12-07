package ru.practicum.exploreWithMe.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.comments.dto.CommentDto;
import ru.practicum.exploreWithMe.comments.mapper.CommentsMapper;
import ru.practicum.exploreWithMe.comments.model.Comment;
import ru.practicum.exploreWithMe.comments.service.CommentService;
import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.dto.UpdateEventRequest;
import ru.practicum.exploreWithMe.enums.State;
import ru.practicum.exploreWithMe.exception.NotFoundException;
import ru.practicum.exploreWithMe.exception.ValidateException;
import ru.practicum.exploreWithMe.event.model.Event;
import ru.practicum.exploreWithMe.category.repository.CategoryRepository;
import ru.practicum.exploreWithMe.event.repository.EventRepository;
import ru.practicum.exploreWithMe.utils.FromSizeRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static ru.practicum.exploreWithMe.event.mapper.EventFullMapper.eventToEventFullDto;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EventAdminServiceImpl implements EventAdminService {

    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final CommentService commentService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
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

        if (states == null) {
            states = new ArrayList<>();
            states.add(State.PENDING);
            states.add(State.CANCELED);
            states.add(State.PUBLISHED);
        }

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
        List<EventFullDto> eventFullDtos = new ArrayList<>();
        events.forEach(e -> {
//            Collection<Comment> comments = commentService.findAllCommentsByEvent(e.getId());
            eventFullDtos.add(eventToEventFullDto(e, null));
        });

        log.info("get events");
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
        log.info("update Event: " + eventId);
        Collection<Comment> comments = commentService.findAllCommentsByEvent(event.getId());
        return eventToEventFullDto(event, comments);
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
        EventFullDto eventDto = eventToEventFullDto(eventRepository.save(event), null);
        log.info("publish Event: " + eventId);
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
        Collection<Comment> comments = commentService.findAllCommentsByEvent(event.getId());
        EventFullDto eventDto = eventToEventFullDto(eventRepository.save(event), comments);
        log.info("reject Event: " + eventId);
        return eventDto;
    }
}


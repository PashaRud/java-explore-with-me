package ru.practicum.exploreWithMe.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.event.model.Event;
import ru.practicum.exploreWithMe.event.repository.EventRepository;
import ru.practicum.exploreWithMe.exception.AlreadyExistsException;
import ru.practicum.exploreWithMe.exception.NotFoundException;
import ru.practicum.exploreWithMe.user.dto.UserDislikeDto;
import ru.practicum.exploreWithMe.user.dto.UserLikesDto;
import ru.practicum.exploreWithMe.user.model.User;
import ru.practicum.exploreWithMe.user.repository.UserRepository;
import ru.practicum.exploreWithMe.utils.FromSizeRequest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.exploreWithMe.event.mapper.EventFullMapper.eventToEventShortDto;
import static ru.practicum.exploreWithMe.user.mapper.UserMapper.toUserDisikesDto;
import static ru.practicum.exploreWithMe.user.mapper.UserMapper.toUserLikesDto;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserLikesServiceImpl implements UserLikesService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserLikesDto addLikesForTheEvent(Long userId, Long eventId) {
        eventValidation(eventId);
        userValidation(userId);
        User user = userRepository.findById(userId).get();
        Event event = eventRepository.findById(eventId).get();
        if(isDisliked(userId, event)) {
            removeDislikesForTheEvent(userId, eventId);
        }
        if(user.getLikes().contains(event)) {
            throw new AlreadyExistsException("Event already liked");
        }
        user.getLikes().add(event);
        return toUserLikesDto(user);
    }

    @Override
    @Transactional
    public void removeLikesForTheEvent(Long userId, Long eventId) {
        eventValidation(eventId);
        userValidation(userId);
        User user = userRepository.findById(userId).get();
        Event event = eventRepository.findById(eventId).get();
        if(!user.getLikes().contains(event)) {
            throw new NotFoundException("You doesn't liked this event: " + eventId);
        }
        user.getLikes().remove(event);
    }

    @Override
    public Set<EventShortDto> getEventLikedByUser(Long userId, int from, int size) {
        userValidation(userId);
        Pageable pageable = FromSizeRequest.of(from, size);
        List<Event> events = eventRepository.findEventsByUserLikes(userId, pageable);
                Set<EventShortDto> eventShortDtos = events.stream()
                        .map(event -> eventToEventShortDto(event))
                        .collect(Collectors.toSet());
        log.info("Get liked events for userId: " + userId);
        return eventShortDtos;
    }

    @Override
    @Transactional
    public UserDislikeDto addDislikesForTheEvent(Long userId, Long eventId) {
        eventValidation(eventId);
        userValidation(userId);
        User user = userRepository.findById(userId).get();
        Event event = eventRepository.findById(eventId).get();
        if(isLiked(userId, event)) {
            removeLikesForTheEvent(userId, eventId);
        }
        if(user.getLikes().contains(event)) {
            throw new AlreadyExistsException("Event already disliked");
        }
        user.getDislikes().add(event);
        return toUserDisikesDto(user);
    }

    @Override
    @Transactional
    public void removeDislikesForTheEvent(Long userId, Long eventId) {
        eventValidation(eventId);
        userValidation(userId);
        User user = userRepository.findById(userId).get();
        Event event = eventRepository.findById(eventId).get();
        if(!user.getDislikes().contains(event)) {
            throw new NotFoundException("You doesn't dislike this event: " + eventId);
        }
        user.getDislikes().remove(event);
    }

    @Override
    public Set<EventShortDto> getEventDislikedByUser(Long userId, int from, int size) {
        userValidation(userId);
        Pageable pageable = FromSizeRequest.of(from, size);
        List<Event> events = eventRepository.findEventsByUserDislikes(userId, pageable);
        Set<EventShortDto> eventShortDtos = events.stream()
                .map(event -> eventToEventShortDto(event))
                .collect(Collectors.toSet());
        log.info("Get disliked events for userId: " + userId);
        return eventShortDtos;
    }

    private void eventValidation(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new NotFoundException("Event with id = " + id + " not found");
        }
    }

    private void userValidation(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with id = " + id + " not found");
        }
    }

    private boolean isLiked(Long userId, Event event) {
        if(userRepository.findById(userId).get()
                .getLikes().contains(event)) {
            return true;
        }
        return false;
    }

    private boolean isDisliked(Long userId, Event event) {
        if(userRepository.findById(userId).get()
                .getDislikes().contains(event)) {
            return true;
        }
        return false;
    }
}

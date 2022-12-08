package ru.practicum.exploreWithMe.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.event.mapper.EventFullMapper;
import ru.practicum.exploreWithMe.event.model.Event;
import ru.practicum.exploreWithMe.event.repository.EventRepository;
import ru.practicum.exploreWithMe.exception.AlreadyExistsException;
import ru.practicum.exploreWithMe.exception.NotFoundException;
import ru.practicum.exploreWithMe.user.dto.UserLikesDto;
import ru.practicum.exploreWithMe.user.model.User;
import ru.practicum.exploreWithMe.user.repository.UserRepository;
import ru.practicum.exploreWithMe.utils.FromSizeRequest;

import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.exploreWithMe.user.mapper.UserMapper.toUserLikesDto;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class UserLikesServiceImpl implements UserLikesService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public UserLikesDto addLikesForTheEvent(Long userId, Long eventId) {
        eventValidation(eventId);
        userValidation(userId);
        User user = userRepository.findById(userId).get();
        Event event = eventRepository.findById(eventId).get();
        if(user.getLikes().contains(event)) {
            throw new AlreadyExistsException("Event already liked");
        }
        user.getLikes().add(eventRepository.findById(eventId).get());
        return toUserLikesDto(user);
    }

    @Override
    public void removeLikesForTheEvent(Long userId, Long eventId) {
        eventValidation(eventId);
        userValidation(userId);
        User user = userRepository.findById(userId).get();
        Event event = eventRepository.findById(eventId).get();
        if(user.getLikes().contains(event)) {
            throw new NotFoundException("You doesn't liked this event: " + eventId);
        }
        user.getLikes().remove(eventRepository.findById(eventId));
    }

    @Override
    public Set<EventShortDto> getEventLikedByUser(Long userId, int from, int size) {
        userValidation(userId);
        Pageable pageable = FromSizeRequest.of(from, size);
        Set<EventShortDto> eventDtos = eventRepository.findAll(pageable).stream()
                .map(EventFullMapper::eventToEventShortDto)
                .collect(Collectors.toSet());
        log.info("get liked events for userId: " + userId);
        return eventDtos;
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
}

package ru.practicum.exploreWithMe.user.service;

import ru.practicum.exploreWithMe.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.event.model.Event;
import ru.practicum.exploreWithMe.user.dto.UserDto;
import ru.practicum.exploreWithMe.user.dto.UserLikesDto;

import java.util.Set;

public interface UserLikesService {

    UserLikesDto addLikesForTheEvent(Long userId, Long EventId);

    void removeLikesForTheEvent(Long userId, Long EventId);

    Set<EventShortDto> getEventLikedByUser(Long userId, int from, int size);
}

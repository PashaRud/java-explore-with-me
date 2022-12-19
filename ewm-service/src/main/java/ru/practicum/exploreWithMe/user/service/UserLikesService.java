package ru.practicum.exploreWithMe.user.service;

import ru.practicum.exploreWithMe.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.user.dto.UserDislikeDto;
import ru.practicum.exploreWithMe.user.dto.UserLikesDto;

import java.util.Set;

public interface UserLikesService {

    UserLikesDto addLikesForTheEvent(Long userId, Long eventId);

    void removeLikesForTheEvent(Long userId, Long eventId);

    Set<EventShortDto> getEventLikedByUser(Long userId, int from, int size);

    UserDislikeDto addDislikesForTheEvent(Long userId, Long eventId);

    void removeDislikesForTheEvent(Long userId, Long eventId);

    Set<EventShortDto> getEventDislikedByUser(Long userId, int from, int size);
}

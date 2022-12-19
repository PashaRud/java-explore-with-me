package ru.practicum.exploreWithMe.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.user.dto.UserDislikeDto;
import ru.practicum.exploreWithMe.user.dto.UserLikesDto;
import ru.practicum.exploreWithMe.user.service.UserLikesService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;

@RestController
@Slf4j
@Validated
@RequestMapping(path = "/users/{userId}")
@RequiredArgsConstructor
public class UserPrivateController {

    private final UserLikesService userService;

    @GetMapping(value = "/likes")
    public Set<EventShortDto> getEventLikedByUser(@PathVariable Long userId,
                                                  @RequestParam(name = "from", defaultValue = "0")
                                                  @PositiveOrZero int from,
                                                  @RequestParam(name = "size", defaultValue = "10")
                                                  @Positive int size) {
        log.info("get liked events by userId: " + userId);
        return userService.getEventLikedByUser(userId, from, size);
    }

    @PostMapping(value = "/likes/{eventId}")
    public UserLikesDto addLikesForTheEvent(@PathVariable Long userId,
                                            @PathVariable Long eventId) {
        log.info("add likes by userId: " + userId + " for the event: " + eventId);
        return userService.addLikesForTheEvent(userId, eventId);
    }

    @DeleteMapping(value = "/likes/{eventId}")
    public void removeLikesForTheEvent(@PathVariable Long userId,
                                       @PathVariable Long eventId) {
        log.info("remove likes by userId: " + userId + " for the event: " + eventId);
        userService.removeLikesForTheEvent(userId, eventId);
    }

    @GetMapping(value = "/dislike")
    public Set<EventShortDto> getEventDislikeByUser(@PathVariable Long userId,
                                                  @RequestParam(name = "from", defaultValue = "0")
                                                  @PositiveOrZero int from,
                                                  @RequestParam(name = "size", defaultValue = "10")
                                                  @Positive int size) {
        log.info("get dislike events by userId: " + userId);
        return userService.getEventDislikedByUser(userId, from, size);
    }

    @PostMapping(value = "/dislike/{eventId}")
    public UserDislikeDto addDislikeForTheEvent(@PathVariable Long userId,
                                                @PathVariable Long eventId) {
        log.info("add dislike by userId: " + userId + " for the event: " + eventId);
        return userService.addDislikesForTheEvent(userId, eventId);
    }

    @DeleteMapping(value = "/dislike/{eventId}")
    public void removeDislikeForTheEvent(@PathVariable Long userId,
                                       @PathVariable Long eventId) {
        log.info("remove dislike by userId: " + userId + " for the event: " + eventId);
        userService.removeDislikesForTheEvent(userId, eventId);
    }

    //abc
}

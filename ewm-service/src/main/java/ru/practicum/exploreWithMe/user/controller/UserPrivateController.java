package ru.practicum.exploreWithMe.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.event.model.Event;
import ru.practicum.exploreWithMe.user.dto.UserDto;
import ru.practicum.exploreWithMe.user.dto.UserLikesDto;
import ru.practicum.exploreWithMe.user.service.UserLikesService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Set;


@RestController
@Slf4j
@Validated
@RequestMapping(path = "/users/{userId}/likes")
@RequiredArgsConstructor
public class UserPrivateController {

    private final UserLikesService userService;

    @GetMapping
    public Set<EventShortDto> getEventLikedByUser(@RequestParam Long userId,
                                                  @RequestParam(name = "from", defaultValue = "0")
                                          @PositiveOrZero int from,
                                                  @RequestParam(name = "size", defaultValue = "10")
                                              @Positive int size) {
        log.info("get liked events by userId: " + userId);
        return userService.getEventLikedByUser(userId, from, size);
    }

    @PostMapping(value = "/{eventId}")
    public UserLikesDto addLikesForTheEvent(@RequestParam Long userId,
                                            @RequestParam Long eventId) {
        log.info("add likes by userId: " + userId + " for the event: " + eventId);
        return userService.addLikesForTheEvent(userId, eventId);
    }

    @DeleteMapping(value = "/{eventId}")
    public void removeLikesForTheEvent(@RequestParam Long userId,
                                       @RequestParam Long eventId) {
        log.info("remove likes by userId: " + userId + " for the event: " + eventId);
        userService.removeLikesForTheEvent(userId, eventId);
    }
}

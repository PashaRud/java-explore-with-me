package ru.practicum.exploreWithMe.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.event.dto.NewEventDto;
import ru.practicum.exploreWithMe.event.dto.UpdateEventRequest;
import ru.practicum.exploreWithMe.request.dto.ParticipationRequestDto;
import ru.practicum.exploreWithMe.event.service.EventPrivateService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class EventPrivateController {

    private final EventPrivateService eventService;

    @GetMapping
    List<EventShortDto> userEvents(@PathVariable Long userId,
                                   @RequestParam(defaultValue = "0") int from,
                                   @RequestParam(defaultValue = "10") int size) {
        return eventService.getEventsOfUser(userId, from, size);
    }

    @PatchMapping
    EventFullDto updateEvent(@PathVariable Long userId,
                              @RequestBody UpdateEventRequest event) {
        return eventService.updateEvent(userId, event);
    }


    @PostMapping
    EventFullDto createEvent(@PathVariable Long userId,
                             @Valid @RequestBody NewEventDto event) {
        return eventService.postEvent(userId, event);
    }

    @GetMapping(value = "{eventId}")
    EventFullDto getEventByUserIdAndEventId(@PathVariable Long userId,
                                            @PathVariable Long eventId) {
        return eventService.getEvent(userId, eventId);
    }

    @PatchMapping(value = "{eventId}")
    EventFullDto cancelEventByUserIdAndEventId(@PathVariable Long userId,
                                               @PathVariable Long eventId) {
        return eventService.cancelEvent(userId, eventId);
    }

    @GetMapping(value = "{eventId}/requests")
    List<ParticipationRequestDto> getRequestsInfoByUserIdAndEvenId(@PathVariable Long userId,
                                                            @PathVariable Long eventId) {
        return eventService.getRequests(userId, eventId);
    }

    @PatchMapping(value = "{eventId}/requests/{reqId}/confirm")
    ParticipationRequestDto confirmParticipationRequest(@PathVariable Long userId,
                                                        @PathVariable Long eventId,
                                                        @PathVariable Long reqId) {
        return eventService.confirmRequest(userId, eventId, reqId);
    }
    @PatchMapping(value = "{eventId}/requests/{reqId}/reject")
    ParticipationRequestDto rejectParticipationRequest(@PathVariable Long userId,
                                                       @PathVariable Long eventId,
                                                       @PathVariable Long reqId) {
        return eventService.rejectRequest(userId, eventId, reqId);
    }
}


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
        List<EventShortDto> dtos = eventService.getEventsOfUser(userId, from, size);
        log.info("user Events: " + userId);
        return dtos;
    }

    @PatchMapping
    EventFullDto updateEvent(@PathVariable Long userId,
                              @RequestBody UpdateEventRequest event) {
        EventFullDto dto = eventService.updateEvent(userId, event);
        log.info("update Event: " + userId);
        return dto;
    }


    @PostMapping
    EventFullDto createEvent(@PathVariable Long userId,
                             @Valid @RequestBody NewEventDto event) {
        EventFullDto dto = eventService.postEvent(userId, event);
        log.info("create Event: " + userId);
        return dto;
    }

    @GetMapping(value = "{eventId}")
    EventFullDto getEventByUserIdAndEventId(@PathVariable Long userId,
                                            @PathVariable Long eventId) {
        EventFullDto dto = eventService.getEvent(userId, eventId);
        log.info("get Event By UserId: " + userId + "And Event Id: " + eventId);
        return dto;
    }

    @PatchMapping(value = "{eventId}")
    EventFullDto cancelEventByUserIdAndEventId(@PathVariable Long userId,
                                               @PathVariable Long eventId) {
        EventFullDto dto = eventService.cancelEvent(userId, eventId);
        log.info("cancel Event By UserId: " + userId + "And Event Id: " + eventId);
        return dto;
    }

    @GetMapping(value = "{eventId}/requests")
    List<ParticipationRequestDto> getRequestsInfoByUserIdAndEvenId(@PathVariable Long userId,
                                                            @PathVariable Long eventId) {
        List<ParticipationRequestDto> dtos = eventService.getRequests(userId, eventId);
        log.info("get RequestsInfo By UserId: " + userId + "And Event Id: " + eventId);
        return dtos;
    }

    @PatchMapping(value = "{eventId}/requests/{reqId}/confirm")
    ParticipationRequestDto confirmParticipationRequest(@PathVariable Long userId,
                                                        @PathVariable Long eventId,
                                                        @PathVariable Long reqId) {
        ParticipationRequestDto dto = eventService.confirmRequest(userId, eventId, reqId);
        log.info("confirm Participation Request. EventId:" + eventId + " . userId: " + userId);
        return dto;
    }

    @PatchMapping(value = "{eventId}/requests/{reqId}/reject")
    ParticipationRequestDto rejectParticipationRequest(@PathVariable Long userId,
                                                       @PathVariable Long eventId,
                                                       @PathVariable Long reqId) {
        ParticipationRequestDto dto = eventService.rejectRequest(userId, eventId, reqId);
        log.info("reject Participation Request. EventId:" + eventId + " . userId: " + userId);
        return dto;
    }
}


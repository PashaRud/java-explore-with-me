package ru.practicum.exploreWithMe.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.dto.event.EventFullDto;
import ru.practicum.exploreWithMe.dto.event.EventShortDto;
import ru.practicum.exploreWithMe.dto.event.NewEventDto;
import ru.practicum.exploreWithMe.dto.event.UpdateEventRequest;
import ru.practicum.exploreWithMe.dto.request.ParticipationRequestDto;
import ru.practicum.exploreWithMe.service.event.EventPrivateService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class EventPrivateController {

    private final EventPrivateService service;

    @GetMapping
    List<EventShortDto> userEvents(@PathVariable Long userId,
                                   @RequestParam(defaultValue = "0") int from,
                                   @RequestParam(defaultValue = "10") int size) {
        return service.getEventsOfUser(userId, from, size);
    }

    @PatchMapping
    EventFullDto updateEvent(@PathVariable Long userId,
                              @RequestBody UpdateEventRequest event) {
        return service.updateEvent(userId, event);
    }


    @PostMapping
    EventFullDto createEvent(@PathVariable Long userId,
                             @RequestBody NewEventDto event) {
        return service.postEvent(userId, event);
    }

    @GetMapping(value = "{eventId}")
    EventFullDto getEventByUserIdAndEventId(@PathVariable Long userId,
                                            @PathVariable Long eventId) {
        return service.getEvent(userId, eventId);
    }

    @PatchMapping(value = "{eventId}")
    EventFullDto cancelEventByUserIdAndEventId(@PathVariable Long userId,
                                               @PathVariable Long eventId) {
        return service.cancelEvent(userId, eventId);
    }

    @GetMapping(value = "{eventId}/requests")
    List<ParticipationRequestDto> getRequestsInfoByUserIdAndEvenId(@PathVariable Long userId,
                                                            @PathVariable Long eventId) {
        return service.getRequests(userId, eventId);
    }

    @PatchMapping(value = "{eventId}/requests/{reqId}/confirm")
    ParticipationRequestDto confirmParticipationRequest(@PathVariable Long userId,
                                                        @PathVariable Long eventId,
                                                        @PathVariable Long reqId) {
        return service.confirmRequest(userId, eventId, reqId);
    }
    @PatchMapping(value = "{eventId}/requests/{reqId}/reject")
    ParticipationRequestDto rejectParticipationRequest(@PathVariable Long userId,
                                                       @PathVariable Long eventId,
                                                       @PathVariable Long reqId) {
        return service.rejectRequest(userId, eventId, reqId);
    }
}


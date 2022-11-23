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
                             @RequestBody NewEventDto event) {
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


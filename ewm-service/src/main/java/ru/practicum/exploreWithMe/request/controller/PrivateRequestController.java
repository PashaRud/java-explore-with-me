package ru.practicum.exploreWithMe.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.exception.ValidateException;
import ru.practicum.exploreWithMe.request.dto.ParticipationRequestDto;
import ru.practicum.exploreWithMe.request.service.RequestPrivateService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class PrivateRequestController {

    private final RequestPrivateService service;

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getRequestsOfUser(@PathVariable Long userId) {
        List<ParticipationRequestDto> dtos = service.getRequestsByUserId(userId);
        log.info("get Requests of userId: " + userId);
        return dtos;
    }

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto saveRequest(@PathVariable Long userId,
                                  @RequestParam(name = "eventId", required = false) Long eventId) {
        log.info("Creating request userId={}, eventId={}", userId, eventId);
        if (eventId == null) {
            throw new ValidateException("incorrect eventId");
        }
        ParticipationRequestDto dto = service.createRequest(userId, eventId);
        log.info("save Requests. UserId:" + userId + " .Event id: " + eventId);
        return dto;
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Long userId,
                                                 @PathVariable Long requestId) {
        ParticipationRequestDto dto = service.cancelRequest(userId, requestId);
        log.info("cancel Requests. UserId:" + userId + " .request id: " + requestId);
        return dto;
    }
}

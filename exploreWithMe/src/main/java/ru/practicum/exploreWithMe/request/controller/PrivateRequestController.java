package ru.practicum.exploreWithMe.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.request.dto.ParticipationRequestDto;
import ru.practicum.exploreWithMe.request.service.RequestPrivateService;

import java.util.List;

@RestController
@Slf4j
@Validated
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class PrivateRequestController {

    private final RequestPrivateService service;

    @GetMapping("/{userId}/requests")
    public List<ParticipationRequestDto> getRequestsOfUser(@PathVariable Long userId) {
        return service.getRequestsByUserId(userId);
    }

    @PostMapping("/{userId}/requests")
    public ParticipationRequestDto createRequest(@PathVariable Long userId,
                                  @RequestParam(name = "eventId", required = false) Long eventId) {
        return service.createRequest(userId, eventId);
    }

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Long userId,
                                                 @PathVariable Long requestId) {
        return service.cancelRequest(userId, requestId);
    }
}

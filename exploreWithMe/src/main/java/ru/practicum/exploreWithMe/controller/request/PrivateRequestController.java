package ru.practicum.exploreWithMe.controller.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.dto.request.ParticipationRequestDto;
import ru.practicum.exploreWithMe.service.request.RequestPrivateService;

import java.util.List;

@RestController
@Slf4j
@Validated
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateRequestController {

    private final RequestPrivateService service;

    @GetMapping
    public List<ParticipationRequestDto> getRequestsOfUser(@PathVariable Long userId) {
        return service.getRequestsByUserId(userId);
    }

    @PostMapping
    public ParticipationRequestDto postRequest(@PathVariable Long userId,
                                               @RequestParam(name = "eventId") Long eventId) {
        return service.createRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(@PathVariable Long userId,
                                                 @PathVariable Long requestId) {
        return service.cancelRequest(userId, requestId);
    }
}

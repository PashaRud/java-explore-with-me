package ru.practicum.exploreWithMe.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.dto.event.EventFullDto;
import ru.practicum.exploreWithMe.dto.event.EventShortDto;
import ru.practicum.exploreWithMe.enums.EventSort;
import ru.practicum.exploreWithMe.service.event.EventPublicService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
@Validated
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class EventPublicController {

    private final EventPublicService eventService;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @GetMapping
    public List<EventShortDto> getEvents(@RequestParam(required = false, defaultValue = "") String text,
                                         @RequestParam(required = false) List<Long> categories,
                                         @RequestParam(required = false) Boolean paid,
                                         @RequestParam(required = false) String rangeStart,
                                         @RequestParam(required = false) String rangeEnd,
                                         @RequestParam(required = false) Boolean onlyAvailable,
                                         @RequestParam(required = false) EventSort sort,
                                         @Valid @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
                                         @Valid @Positive @RequestParam(defaultValue = "10") Integer size,
                                         HttpServletRequest request) {
        log.info("{}: Запрос к эндпоинту '{}' на получение списка событий",
                request.getRemoteAddr(), request.getRequestURI());
//        statsService.setHits(request.getRequestURI(), request.getRemoteAddr());
        return eventService.getEvents(text, categories, paid,
                LocalDateTime.parse(rangeStart, formatter),
                LocalDateTime.parse(rangeEnd, formatter),
                onlyAvailable, sort, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getEventById(@PathVariable Long eventId) {
        EventFullDto eventFullDto = eventService.getEventById(eventId);
//        statsService.setHits(request.getRequestURI(), request.getRemoteAddr());
        return eventFullDto;
    }
}

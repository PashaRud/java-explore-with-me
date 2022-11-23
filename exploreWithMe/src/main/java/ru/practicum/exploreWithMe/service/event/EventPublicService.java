package ru.practicum.exploreWithMe.service.event;

import org.springframework.data.domain.Sort;
import ru.practicum.exploreWithMe.dto.event.EventFullDto;
import ru.practicum.exploreWithMe.dto.event.EventShortDto;
import ru.practicum.exploreWithMe.enums.EventSort;
import ru.practicum.exploreWithMe.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface EventPublicService {

    List<EventShortDto> getEvents(String text, List<Long> categories, Boolean paid,
                                           LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                           Boolean onlyAvailable, EventSort sort, Integer from, Integer size);
    EventFullDto getEventById(Long eventId);

}

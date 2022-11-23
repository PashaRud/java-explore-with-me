package ru.practicum.exploreWithMe.service.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.exploreWithMe.dto.event.EventFullDto;
import ru.practicum.exploreWithMe.dto.event.EventShortDto;
import ru.practicum.exploreWithMe.enums.EventSort;
import ru.practicum.exploreWithMe.enums.State;
import ru.practicum.exploreWithMe.exception.ForbiddenException;
import ru.practicum.exploreWithMe.exception.NotFoundException;
import ru.practicum.exploreWithMe.exception.ValidateException;
import ru.practicum.exploreWithMe.mapper.event.EventFullMapper;
import ru.practicum.exploreWithMe.model.Event;
import ru.practicum.exploreWithMe.repository.category.CategoryRepository;
import ru.practicum.exploreWithMe.repository.event.EventRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventPublicServiceImpl implements EventPublicService{

    private final EventRepository repository;
    private final EntityManager entityManager;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<EventShortDto> getEvents(String text, List<Long> categories, Boolean paid,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         Boolean onlyAvailable, EventSort sort, Integer from, Integer size) {
        rangeStart = (rangeStart != null) ? rangeStart : LocalDateTime.now();
        rangeEnd = (rangeEnd != null) ? rangeEnd : LocalDateTime.now().plusYears(300);

        if (rangeStart.isAfter(rangeEnd)) {
            throw new ValidateException("The end date and time of the event cannot " +
                    "be earlier than the start date of the event.");
        }

        Session session = entityManager.unwrap(Session.class);

        // в выдаче должны быть только опубликованные события
        session.enableFilter("stateFilter").setParameter("state", State.PUBLISHED.toString());

        // включаем фильтр по датам
        Filter dateFilter = session.enableFilter("dateFilter");
        dateFilter.setParameter("rangeStart", rangeStart);
        dateFilter.setParameter("rangeEnd", rangeEnd);

        // включаем фильтр по платным/бесплатным событиям, если задано
        if (paid != null) {
            session.enableFilter("paidFilter").setParameter("paid", paid);
        }

        List<Event> events;

        if (categories != null) {
            events = repository.findByCategoryIdsAndText(text, categories);
        } else {
            events = repository.findByText(text);
        }


        // выключаем фильтры
        if (paid != null) session.disableFilter("paidFilter");

        session.disableFilter("dateFilter");
        session.disableFilter("stateFilter");

        List<EventShortDto> eventShortDtos = events.stream()
                .map(dtos -> EventFullMapper.eventToEventShortDto(dtos))
                .collect(toList());

        if (onlyAvailable) {
            eventShortDtos = eventShortDtos.stream()
                    .filter(x -> x.getConfirmedRequests() < x.getParticipantLimit())
                    .collect(toList());
        }

        if (EventSort.VIEWS.equals(sort)) {
            eventShortDtos = eventShortDtos.stream()
                    .sorted(Comparator.comparingLong(EventShortDto::getViews))
                    .skip(from)
                    .limit(size)
                    .collect(toList());
        } else {
            eventShortDtos = eventShortDtos.stream()
                    .sorted(Comparator.comparing(EventShortDto::getEventDate))
                    .skip(from)
                    .limit(size)
                    .collect(toList());
        }

        return eventShortDtos;
}

    @Override
    public EventFullDto getEventById(Long id) {
        eventValidation(id);
        EventFullDto dto = EventFullMapper.eventToEventFullDto(repository.findById(id).get());
        if (!State.PUBLISHED.equals(dto.getState())) {
            throw new ForbiddenException("Event not published.");
        }
        log.info("events by id = " + id + " for an unregistered user");
        return dto;

    }

    private void eventValidation(Long id) {
        if (repository.findById(id).isEmpty()) {
            throw new NotFoundException(String.format("Events with id = " + id + " не найдено"));
        }
    }
}

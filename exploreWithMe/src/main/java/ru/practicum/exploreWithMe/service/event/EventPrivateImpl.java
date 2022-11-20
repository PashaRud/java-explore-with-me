package ru.practicum.exploreWithMe.service.event;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.dto.event.EventFullDto;
import ru.practicum.exploreWithMe.dto.event.EventShortDto;
import ru.practicum.exploreWithMe.dto.event.NewEventDto;
import ru.practicum.exploreWithMe.dto.event.UpdateEventRequest;
import ru.practicum.exploreWithMe.dto.request.ParticipationRequestDto;
import ru.practicum.exploreWithMe.exception.ForbiddenException;
import ru.practicum.exploreWithMe.exception.NotFoundException;
import ru.practicum.exploreWithMe.mapper.event.EventFullMapper;
import ru.practicum.exploreWithMe.repository.event.EventRepository;
import ru.practicum.exploreWithMe.repository.user.UserRepository;
import ru.practicum.exploreWithMe.utils.FromSizeRequest;

import javax.persistence.EntityManager;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventPrivateImpl implements EventPrivateService {

    private final EventRepository repository;
    private final EntityManager entityManager;
    private final EventFullMapper mapper;
    private final UserRepository userRepository;

    @Override
    public List<EventShortDto> getEventsOfUser(Long userId, int from, int size) {
        userValidation(userId);
        Pageable pageable = FromSizeRequest.of(from, size);

        List<EventShortDto> eventShortDtos = repository.findByInitiatorId(userId, pageable).stream()
                .map(mapper::eventToEventShortDto)
                .collect(toList());
        return eventShortDtos;
    }

    @Override
    public EventFullDto updateEvent(Long userId, UpdateEventRequest event) {
        userValidation(userId);
        eventValidation(event.getEventId());
        if (!userId.equals()) {
            throw new ForbiddenException("Отредактировать событие может только инициатор!");
        }
        return null;
    }

    @Override
    public EventFullDto postEvent(Long userId, NewEventDto dto) {
        return null;
    }

    @Override
    public EventFullDto getEvent(Long userId, Long eventId) {
        return null;
    }

    @Override
    public EventFullDto cancelEvent(Long userId, Long eventId) {
        return null;
    }

    @Override
    public List<ParticipationRequestDto> getRequests(Long userId, Long eventId) {
        return null;
    }

    @Override
    public ParticipationRequestDto confirmRequest(Long userId, Long eventId, Long reqId) {
        return null;
    }

    @Override
    public ParticipationRequestDto rejectRequest(Long userId, Long eventId, Long reqId) {
        return null;
    }

    private void userValidation(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException(String.format("Пользователь с id = '%s' не найден", id));
        }
    }

    private void eventValidation(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(String.format("Событие с id = '%s' не найдено", id));
        }
    }
}

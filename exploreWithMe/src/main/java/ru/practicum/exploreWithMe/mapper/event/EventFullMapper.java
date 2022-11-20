package ru.practicum.exploreWithMe.mapper.event;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import ru.practicum.exploreWithMe.dto.categories.CategoryDto;
import ru.practicum.exploreWithMe.dto.event.EventFullDto;
import ru.practicum.exploreWithMe.dto.event.EventShortDto;
import ru.practicum.exploreWithMe.dto.event.NewEventDto;
import ru.practicum.exploreWithMe.dto.location.Location;
import ru.practicum.exploreWithMe.dto.user.UserDto;
import ru.practicum.exploreWithMe.dto.user.UserShortDto;
import ru.practicum.exploreWithMe.enums.State;

import ru.practicum.exploreWithMe.mapper.user.UserMapper;
import ru.practicum.exploreWithMe.model.Category;
import ru.practicum.exploreWithMe.model.Event;
import ru.practicum.exploreWithMe.model.User;

import java.time.LocalDateTime;

@Component
public class EventFullMapper {

    public NewEventDto newEventDtoToNewEventDto(NewEventDto newEventDto, UserDto initiator,
                                                   CategoryDto categoryDto) {
        if (newEventDto == null) {
            return null;
        }

        NewEventDto eventCreateDto = NewEventDto.builder()
                .annotation(newEventDto.getAnnotation())
                .category(categoryDto)
                .description(newEventDto.getDescription())
                .eventDate(newEventDto.getEventDate())
                .initiator(UserMapper.toUser(initiator))
                .paid(newEventDto.isPaid())
                .location(newEventDto.getLocation())
                .participantLimit(newEventDto.getParticipantLimit())
                .requestModeration(newEventDto.isRequestModeration())
                .title(newEventDto.getTitle())
                .createdOn(LocalDateTime.now())
                .build();
        return eventCreateDto;
    }

    public Event eventCreateDtoToEvent(NewEventDto eventCreateDto) {
        if (eventCreateDto == null) {
            return null;
        }

        Event event = Event.builder()
                .id(null)
                .title(eventCreateDto.getTitle())
                .annotation(eventCreateDto.getAnnotation())
                .category(new Category(eventCreateDto.getCategory().getId(),
                        eventCreateDto.getCategory().getName()))
                .description(eventCreateDto.getDescription())
                .eventDate(eventCreateDto.getEventDate())
                .lon(eventCreateDto.getLocation().getLon())
                .lat(eventCreateDto.getLocation().getLat())
                .initiator(new User(eventCreateDto.getInitiator().getId(),
                        eventCreateDto.getInitiator().getEmail(),
                        eventCreateDto.getInitiator().getName()))
                .createdOn(eventCreateDto.getCreatedOn())
                .paid(eventCreateDto.isPaid())
                .participantLimit(eventCreateDto.getParticipantLimit())
                .requestModeration(eventCreateDto.isRequestModeration())
                .state(State.PENDING)
                .build();
        return event;
    }

    public EventFullDto eventToEventFullDto(Event event) {

        EventFullDto eventFullDto = EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(new CategoryDto(event.getCategory().getId(), event.getCategory().getName()))
//              Исправить на confirmedRequests
                .confirmedRequests(1L)
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .initiator(new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()))
                .location(new Location(event.getLat(), event.getLon()))
                .paid(event.isPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .requestModeration(event.isRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .build();

        return eventFullDto;
    }

    public EventShortDto eventToEventShortDto(Event event) {

        EventShortDto eventShortDto = EventShortDto.builder()
                .id(event.getId())
//                .annotation(event.getAnnotation())
                .category(new CategoryDto(event.getCategory().getId(), event.getCategory().getName()))
//                .confirmedRequests(confirmedRequests)
                .confirmedRequests(1L)
                .eventDate(event.getEventDate())
                .initiator(new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()))
                .paid(event.isPaid())
                .title(event.getTitle())
                .build();

        return eventShortDto;
    }
}

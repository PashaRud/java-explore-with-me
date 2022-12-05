package ru.practicum.exploreWithMe.event.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.dto.EventShortDto;
import ru.practicum.exploreWithMe.location.Location;
import ru.practicum.exploreWithMe.enums.State;

import ru.practicum.exploreWithMe.category.mapper.CategoryMapper;
import ru.practicum.exploreWithMe.user.mapper.UserMapper;
import ru.practicum.exploreWithMe.event.model.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class EventFullMapper {




    public static Event toEvent(EventFullDto eventDto) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Event.builder()
                .id(eventDto.getId())
                .title(eventDto.getTitle())
                .annotation(eventDto.getAnnotation())
                .category(CategoryMapper.categoryDtoToCategory(eventDto.getCategory()))
                .eventDate(LocalDateTime.parse(eventDto.getEventDate(), formatter))
                .lon(eventDto.getLocation().getLon())
                .lat(eventDto.getLocation().getLat())
                .initiator(UserMapper.toUserFromShortDto(eventDto.getInitiator()))
                .createdOn(LocalDateTime.parse(eventDto.getCreatedOn(), formatter))
                .paid(eventDto.isPaid())
                .participantLimit(eventDto.getParticipantLimit())
                .requestModeration(eventDto.isRequestModeration())
                .state(State.PENDING)
                .build();
    }

    public static EventFullDto eventToEventFullDto(Event event) {

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn().format(formatter))
                .description(event.getDescription())
                .eventDate(event.getEventDate().format(formatter))
                .initiator(UserMapper.toShortUserDto(event.getInitiator()))
                .location(new Location(event.getLat(), event.getLon()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn().format(formatter))
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .build();
    }

    public static EventShortDto eventToEventShortDto(Event event) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toCategoryDto(event.getCategory()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().format(formatter))
                .initiator(UserMapper.toShortUserDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
                .build();
    }
}

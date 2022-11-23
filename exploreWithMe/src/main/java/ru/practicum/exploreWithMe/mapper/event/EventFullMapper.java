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

import ru.practicum.exploreWithMe.mapper.categories.CategoryMapper;
import ru.practicum.exploreWithMe.mapper.user.UserMapper;
import ru.practicum.exploreWithMe.model.Category;
import ru.practicum.exploreWithMe.model.Event;
import ru.practicum.exploreWithMe.model.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class EventFullMapper {




    public static Event toEvent(EventFullDto eventDto) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Event.builder()
                .id(eventDto.getId())
                .title(eventDto.getTitle())
                .annotation(eventDto.getAnnotation())
                .category(CategoryMapper.CategoryDtoToCategory(eventDto.getCategory()))
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

//    public static List<EventShortDto> toEventDtoList(Set<Event> events) {
//        List<EventShortDto> result = new ArrayList<>();
//        for (Event event : events) {
//            result.add(eventToEventShortDto(event));
//        }
//        return result;
//    }
}

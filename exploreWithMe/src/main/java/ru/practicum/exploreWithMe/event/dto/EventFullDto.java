package ru.practicum.exploreWithMe.event.dto;

import lombok.*;
import ru.practicum.exploreWithMe.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.location.Location;
import ru.practicum.exploreWithMe.user.dto.UserShortDto;
import ru.practicum.exploreWithMe.enums.State;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventFullDto {
    private Long id;
    @NotBlank
    private String annotation;
    private CategoryDto category;
    private Integer confirmedRequests;
    private String createdOn;
    private String description;
    @NotBlank
    private String eventDate;
    @NotBlank
    private UserShortDto initiator;
    @NotBlank
    private Location location;
    @NotBlank
    private boolean paid;
    private int participantLimit;
    private String publishedOn;
    private boolean requestModeration;
    @NotBlank
    private String title;
    private Long views;
    private State state;
}

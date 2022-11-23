package ru.practicum.exploreWithMe.dto.event;

import lombok.*;
import ru.practicum.exploreWithMe.dto.categories.CategoryDto;
import ru.practicum.exploreWithMe.dto.location.Location;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUpdateEventRequest {
    private String annotation;
    private CategoryDto category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String title;
}

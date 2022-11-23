package ru.practicum.exploreWithMe.dto.event;

import lombok.*;
import ru.practicum.exploreWithMe.dto.categories.CategoryDto;
import ru.practicum.exploreWithMe.dto.location.Location;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEventRequest {
    @NotBlank
    private Long eventId;
    @NotBlank
    private String annotation;
    @NotBlank
    private Long categoryId;
    private String description;
    @NotBlank
    private Location location;
    @NotBlank
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private String eventDate;
    @NotBlank
    private String title;
}

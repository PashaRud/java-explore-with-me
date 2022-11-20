package ru.practicum.exploreWithMe.dto.event;

import lombok.*;
import ru.practicum.exploreWithMe.dto.categories.CategoryDto;
import ru.practicum.exploreWithMe.dto.location.Location;
import ru.practicum.exploreWithMe.dto.user.UserShortDto;
import ru.practicum.exploreWithMe.model.User;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewEventDto {
    @NotBlank
    private String annotation;
    @NotBlank
    private CategoryDto category;
    private String description;
    @NotBlank
    private LocalDateTime eventDate;
    @NotBlank
    @NotBlank
    private Location location;
    @NotBlank
    private boolean paid;
    private User initiator;
    private int participantLimit;
    private boolean requestModeration;
    @NotBlank
    private String title;
    private LocalDateTime createdOn;
}

package ru.practicum.exploreWithMe.dto.event;

import lombok.*;
import ru.practicum.exploreWithMe.dto.categories.CategoryDto;
import ru.practicum.exploreWithMe.dto.location.Location;
import ru.practicum.exploreWithMe.dto.user.UserDto;
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
    private Long category;
    private String description;
    @NotBlank
    private String eventDate;
    @NotBlank
    @NotBlank
    private Location location;
    @NotBlank
    private boolean paid;
//    private UserDto initiator;
    private Integer participantLimit;
    private Boolean requestModeration;
    @NotBlank
    private String title;
}

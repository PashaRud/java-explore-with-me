package ru.practicum.exploreWithMe.dto.event;

import lombok.*;
import ru.practicum.exploreWithMe.dto.categories.CategoryDto;

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
    private Integer categoryId;
    private String description;
    @NotBlank
    private LocalDateTime eventDate;
    @NotBlank
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    @NotBlank
    private String title;
}

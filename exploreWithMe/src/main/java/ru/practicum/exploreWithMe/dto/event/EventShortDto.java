package ru.practicum.exploreWithMe.dto.event;

import lombok.*;
import ru.practicum.exploreWithMe.dto.categories.CategoryDto;
import ru.practicum.exploreWithMe.dto.user.UserShortDto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventShortDto {
    private Long id;
    private String annotation;
    @NotBlank
    private CategoryDto category;
    private Integer confirmedRequests;
    @NotBlank
    private String eventDate;
    @NotBlank
    private UserShortDto initiator;
    private Integer participantLimit;
    @NotBlank
    private boolean paid;
    @NotBlank
    private String title;
    private Long views;
}

package ru.practicum.exploreWithMe.event.dto;

import lombok.*;
import ru.practicum.exploreWithMe.category.dto.CategoryDto;
import ru.practicum.exploreWithMe.user.dto.UserShortDto;

import javax.validation.constraints.NotBlank;

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

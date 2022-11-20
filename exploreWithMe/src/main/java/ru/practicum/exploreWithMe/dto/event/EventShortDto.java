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
    @NotBlank
    private CategoryDto category;
    private Long confirmedRequests;
    @NotBlank
    private LocalDateTime eventDate;
    @NotBlank
    private UserShortDto initiator;
    @NotBlank
    private boolean paid;
    @NotBlank
    private String title;
    private Long views;
}

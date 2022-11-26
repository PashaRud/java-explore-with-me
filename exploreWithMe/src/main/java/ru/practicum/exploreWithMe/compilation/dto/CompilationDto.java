package ru.practicum.exploreWithMe.compilation.dto;

import lombok.*;
import ru.practicum.exploreWithMe.event.dto.EventShortDto;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompilationDto {
    private Set<EventShortDto> events;
    @NotBlank
    private Long id;
    @NotBlank
    private boolean pinned;
    @NotBlank
    private String title;
}

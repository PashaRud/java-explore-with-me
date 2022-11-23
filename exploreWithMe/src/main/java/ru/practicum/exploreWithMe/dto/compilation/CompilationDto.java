package ru.practicum.exploreWithMe.dto.compilation;

import lombok.*;
import ru.practicum.exploreWithMe.dto.event.EventShortDto;

import javax.validation.constraints.NotBlank;
import java.util.List;
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

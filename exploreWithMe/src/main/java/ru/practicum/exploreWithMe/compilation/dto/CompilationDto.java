package ru.practicum.exploreWithMe.compilation.dto;

import lombok.*;
import ru.practicum.exploreWithMe.event.model.Event;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompilationDto {
    private Set<Event> events;
    @NotBlank
    private Long id;
    @NotBlank
    private Boolean pinned;
    @NotBlank
    private String title;
}

package ru.practicum.exploreWithMe.compilation.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCompilationDto {
    private Long id;
    private Set<Long> events;
    private Boolean pinned;
    @NotBlank
    private String title;
}

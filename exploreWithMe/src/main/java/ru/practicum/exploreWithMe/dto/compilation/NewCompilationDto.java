package ru.practicum.exploreWithMe.dto.compilation;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCompilationDto {
    @NotBlank
    private List<Long> events;
    @NotBlank
    private boolean pinned;
    @NotBlank
    private String title;
}

package ru.practicum.exploreWithMe.compilation.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCompilationDto {
    private List<Long> events;
    private boolean pinned;
    @NotBlank
    private String title;
}

package ru.practicum.exploreWithMe.dto.categories;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {
    @NotBlank
    @Positive
    private Long id;
    @NotBlank
    private String name;
}

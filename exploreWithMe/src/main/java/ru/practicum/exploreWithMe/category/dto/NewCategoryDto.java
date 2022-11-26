package ru.practicum.exploreWithMe.category.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class NewCategoryDto {
    @NotBlank
    private String name;
}

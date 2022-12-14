package ru.practicum.exploreWithMe.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank
    private Long id;
    @NotBlank
    private String email;
    @NotBlank
    private String name;
}

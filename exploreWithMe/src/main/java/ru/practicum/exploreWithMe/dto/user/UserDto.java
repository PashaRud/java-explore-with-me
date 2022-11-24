package ru.practicum.exploreWithMe.dto.user;

import lombok.*;

import javax.validation.constraints.Email;
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

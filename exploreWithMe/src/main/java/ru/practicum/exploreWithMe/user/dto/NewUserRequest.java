package ru.practicum.exploreWithMe.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewUserRequest {
    @NotBlank
    private String name;
    @Email
    @NotBlank
    private String email;
}

package ru.practicum.exploreWithMe.comments.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCommentDto {

    @NotBlank
    private String text;
}

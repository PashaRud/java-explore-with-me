package ru.practicum.exploreWithMe.comments.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCommentDto {

    @NotNull
    private Long commentId;
    @NotBlank
    private String text;
}

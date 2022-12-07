package ru.practicum.exploreWithMe.comments.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewCommentDto {

    @NotNull
    private String text;
}

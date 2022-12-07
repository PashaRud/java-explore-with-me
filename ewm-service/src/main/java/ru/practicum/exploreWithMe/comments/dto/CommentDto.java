package ru.practicum.exploreWithMe.comments.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {

    private Long id;
    @NotNull
    private String text;
    @NotNull
    private Long author;
    @NotNull
    private Long event;
    private LocalDateTime create;
}

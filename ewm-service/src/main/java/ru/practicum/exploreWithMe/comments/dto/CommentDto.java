package ru.practicum.exploreWithMe.comments.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {

    private Long id;
    private String text;
    private Long author;
    private Long event;
    private LocalDateTime create;
}

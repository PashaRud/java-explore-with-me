package ru.practicum.exploreWithMe.comments.mapper;

import ru.practicum.exploreWithMe.comments.dto.CommentDto;
import ru.practicum.exploreWithMe.comments.dto.NewCommentDto;
import ru.practicum.exploreWithMe.comments.dto.UpdateCommentDto;
import ru.practicum.exploreWithMe.comments.model.Comment;

import java.time.LocalDateTime;

public class CommentsMapper {

    public static CommentDto fromCommentToCommentDto(Comment comments) {
        return new CommentDto(
                comments.getId(),
                comments.getText(),
                comments.getAuthorId(),
                comments.getEventId(),
                comments.getCreated());
    }

    public static Comment fromNewCommentDto(NewCommentDto commentDto) {
        return Comment.builder()
                .text(commentDto.getText())
                .created(LocalDateTime.now())
                .build();
    }

    public static Comment fromUpdateCommentDto(UpdateCommentDto commentDto) {
        return Comment.builder()
                .id(commentDto.getCommentId())
                .text(commentDto.getText())
                .build();
    }
}

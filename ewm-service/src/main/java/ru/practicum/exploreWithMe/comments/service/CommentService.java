package ru.practicum.exploreWithMe.comments.service;

import ru.practicum.exploreWithMe.comments.dto.CommentDto;
import ru.practicum.exploreWithMe.comments.dto.NewCommentDto;
import ru.practicum.exploreWithMe.comments.dto.UpdateCommentDto;

import java.util.Collection;

public interface CommentService {

    CommentDto createComment(NewCommentDto comment, Long eventId, Long userId);

    CommentDto patchComment(UpdateCommentDto comment, Long eventId, Long userId);

    void deleteCommentById(Long commentId, Long userId);

    void deleteCommentByAdmin(Long commentId);

    Collection<CommentDto> findAllCommentsByEvent(Long eventId, int from, int size);
}

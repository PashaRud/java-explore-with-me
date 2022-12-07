package ru.practicum.exploreWithMe.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.comments.dto.CommentDto;
import ru.practicum.exploreWithMe.comments.dto.NewCommentDto;
import ru.practicum.exploreWithMe.comments.dto.UpdateCommentDto;
import ru.practicum.exploreWithMe.comments.service.CommentService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class CommentPrivateController {

    private final CommentService service;

    @PostMapping(value = "/{userId}/events/{eventId}/comments")
    public CommentDto createComment(@RequestBody @Valid NewCommentDto commentDto,
                                    @PathVariable Long eventId,
                                    @PathVariable Long userId) {
        log.info("Create comment " + commentDto + " for eventId: " + eventId);
        return service.createComment(commentDto, eventId, userId);
    }

    @PatchMapping(value = "/events/{eventId}/comments")
    public CommentDto patchComment(@RequestBody @Valid UpdateCommentDto commentDto,
                                   @PathVariable Long eventId,
                                   @PathVariable Long commentId) {
        log.info("Patch comment " + commentDto + " for eventId: " + eventId);
        return service.patchComment(commentDto, eventId, commentId);
    }

    @DeleteMapping(value = {"/{userId}/comments/{commentId}"})
    public void deleteCommentById(@PathVariable Long userId,
                                  @PathVariable Long commentId) {
        log.info("Delete comment " + commentId + " for eventId: " + userId);
        service.deleteCommentById(commentId, userId);
    }
}

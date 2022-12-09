package ru.practicum.exploreWithMe.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.exploreWithMe.comments.model.service.CommentService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "admin/users")
public class CommentAdminController {

    private final CommentService service;

    @DeleteMapping(value = {"/comments/{commentId}"})
    public void deleteCommentByAdmin(@PathVariable Long commentId) {
        log.info("Delete comment by admin: " + commentId);
        service.deleteCommentByAdmin(commentId);
    }
}

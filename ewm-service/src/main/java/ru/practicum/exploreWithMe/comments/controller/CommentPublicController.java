package ru.practicum.exploreWithMe.comments.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.comments.dto.CommentDto;
import ru.practicum.exploreWithMe.comments.service.CommentService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "events/{eventId}/comments")
public class CommentPublicController {

    private final CommentService service;

    @GetMapping
    public Collection<CommentDto> getAllCommentsByEventId(@PathVariable Long eventId,
                                                          @RequestParam(name = "from", defaultValue = "0")
                                                          @PositiveOrZero int from,
                                                          @RequestParam(name = "size", defaultValue = "10")
                                                          @Positive int size) {
        Collection<CommentDto> comment = service.findAllCommentsByEvent(eventId, from, size);
        log.info("Get all event comments " + eventId);
        return comment;
    }
}

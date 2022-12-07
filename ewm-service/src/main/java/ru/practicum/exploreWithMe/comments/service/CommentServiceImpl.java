package ru.practicum.exploreWithMe.comments.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.exploreWithMe.comments.dto.CommentDto;
import ru.practicum.exploreWithMe.comments.dto.NewCommentDto;
import ru.practicum.exploreWithMe.comments.dto.UpdateCommentDto;
import ru.practicum.exploreWithMe.comments.mapper.CommentsMapper;
import ru.practicum.exploreWithMe.comments.model.Comment;
import ru.practicum.exploreWithMe.comments.repository.CommentRepository;
import ru.practicum.exploreWithMe.event.model.Event;
import ru.practicum.exploreWithMe.event.repository.EventRepository;
import ru.practicum.exploreWithMe.exception.NotFoundException;
import ru.practicum.exploreWithMe.exception.ValidateException;
import ru.practicum.exploreWithMe.user.model.User;
import ru.practicum.exploreWithMe.user.repository.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.exploreWithMe.comments.mapper.CommentsMapper.fromCommentToCommentDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public CommentDto createComment(NewCommentDto commentDto, Long eventId, Long userId) {
        eventValidation(eventId);
        userValidation(userId);
        Event event = eventRepository.findById(eventId).get();
        User user = userRepository.findById(userId).get();
        Comment comment = CommentsMapper.fromNewCommentDto(commentDto);
        comment.setEvent(event);
        comment.setAuthor(user);
        try {
            return fromCommentToCommentDto(repository.save(comment));
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot save comment");
        }
    }

    @Override
    public CommentDto patchComment(UpdateCommentDto commentDto, Long eventId, Long userId) {
        eventValidation(eventId);
        userValidation(userId);
        Comment comment = CommentsMapper.fromUpdateCommentDto(commentDto);
        comment.setText(commentDto.getText());
        try {
            return fromCommentToCommentDto(repository.save(comment));
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Cannot save comment");
        }
    }

    @Override
    public void deleteCommentById(Long commentId, Long userId) {
        Comment comment = getCommentById(commentId, userId);
        repository.deleteById(comment.getId());
    }

    @Override
    public Collection<CommentDto> findAllCommentsByEvent(Long eventId, int from, int size) {
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByEventIdOrderByCreatedDesc(eventId, pageable).toList().stream()
                .map(comment -> fromCommentToCommentDto(comment))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Comment> findAllCommentsByEvent(Long eventId) {
        return repository.findByEventIdOrderByCreatedDesc(eventId);
    }

    private void eventValidation(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new NotFoundException("Event with id = " + id + " not found");
        }
    }

    private void userValidation(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with id = " + id + " not found");
        }
    }

    private Comment getCommentById(Long commentId, Long userId) {
        Optional<Comment> comment = repository.findById(commentId);
        comment.orElseThrow(() -> {
            throw new NotFoundException("Comment with id: " + commentId + " does not exist");
        });
        if (!comment.get().getAuthor().getId().equals(userId)) {
            throw new ValidateException("User with id " + userId + " not the author of the comment c id " + commentId);
        }
        return comment.get();
    }
}

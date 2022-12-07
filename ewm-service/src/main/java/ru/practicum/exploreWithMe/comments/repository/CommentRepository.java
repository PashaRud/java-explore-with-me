package ru.practicum.exploreWithMe.comments.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.exploreWithMe.comments.model.Comment;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByEventIdOrderByCreatedDesc(Long itemId, Pageable pageable);
}

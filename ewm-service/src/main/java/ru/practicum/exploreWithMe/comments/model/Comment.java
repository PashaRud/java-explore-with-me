package ru.practicum.exploreWithMe.comments.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "comments", schema = "public")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "text", nullable = false)
    private String text;
    @Column(name = "author_id")
    private Long authorId;
    @Column(name = "event_id")
    private Long eventId;
    @Column(name = "created_on", nullable = false)
    private LocalDateTime created;
}

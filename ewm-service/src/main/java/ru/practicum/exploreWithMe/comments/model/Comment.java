package ru.practicum.exploreWithMe.comments.model;

import lombok.*;
import ru.practicum.exploreWithMe.event.model.Event;
import ru.practicum.exploreWithMe.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @NotNull
    @Column(name = "text", nullable = false)
    private String text;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
}

package ru.practicum.exploreWithMe.request.model;

import lombok.*;
import ru.practicum.exploreWithMe.enums.State;
import ru.practicum.exploreWithMe.enums.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "requests")
public class Request {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Column(name = "created")
//    private LocalDateTime created;
//    @ManyToOne
//    @JoinColumn(name = "event_id")
//    private Event event;
//    @ManyToOne
//    @JoinColumn(name = "requester_id")
//    private User requester;
//    @Enumerated(EnumType.STRING)
//    @Column(name = "status")
//    private Status status;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "requester_id", nullable = false)
    private Long requester;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;
}

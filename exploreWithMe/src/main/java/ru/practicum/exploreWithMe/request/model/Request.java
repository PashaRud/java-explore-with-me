package ru.practicum.exploreWithMe.request.model;

import lombok.*;
import ru.practicum.exploreWithMe.enums.State;

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

    @Column(name = "requester", nullable = false)
    private Long requester;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private State status;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return id.equals(request.id) && eventId.equals(request.eventId) && requester.equals(request.requester) && status.equals(request.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventId, requester, status);
    }
}

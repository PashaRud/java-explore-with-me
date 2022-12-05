package ru.practicum.exploreWithMe.compilation.model;

import lombok.*;
import ru.practicum.exploreWithMe.event.model.Event;

import javax.persistence.*;
import java.util.Set;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "pinned")
    private boolean pinned;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "events_compilations", joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    private Set<Event> events;

}

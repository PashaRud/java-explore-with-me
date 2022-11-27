package ru.practicum.statictics.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "Statistics")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotBlank
    @Column(name = "uri")
    String uri;
    @NotBlank
    @Column(name = "app")
    String app;
    @NotBlank
    @Column(name = "ip")
    String ip;
    @Column(name = "timestamp")
    LocalDateTime timestamp;
}

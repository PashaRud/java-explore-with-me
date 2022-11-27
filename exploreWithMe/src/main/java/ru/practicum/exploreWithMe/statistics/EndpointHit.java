package ru.practicum.exploreWithMe.statistics;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EndpointHit {
    private Long id;
    private String uri;
    private String app;
    private String ip;
    private String timestamp;
}

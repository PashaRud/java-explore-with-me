package ru.practicum.exploreWithMe.statistics.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class EndPointHit {
    private long id;
    private String uri;
    private String app;
    private String ip;
    private String timestamp;
}

package ru.practicum.exploreWithMe.statistics;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class ViewStats {
    private String uri;
    private String app;
    private Integer hits;
}

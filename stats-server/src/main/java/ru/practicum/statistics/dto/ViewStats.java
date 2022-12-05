package ru.practicum.statistics.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewStats {

    private String app;
    private String uri;
    private Integer hits;
}

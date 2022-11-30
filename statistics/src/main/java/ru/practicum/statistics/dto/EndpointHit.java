package ru.practicum.statistics.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHit {
    private Long id;
    private String uri;
    private String app;
    private String ip;
    private String timestamp;
}

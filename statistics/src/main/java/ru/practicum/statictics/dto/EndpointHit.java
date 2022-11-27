package ru.practicum.statictics.dto;

import lombok.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


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

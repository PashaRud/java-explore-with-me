package ru.practicum.statictics.dto;

import lombok.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EndPointHit {
    private Long id;
    @NotBlank
    private String uri;
    @NotBlank
    private String app;
    @NotBlank
    private String ip;
    private LocalDateTime timestamp;
    private Long hits;
}

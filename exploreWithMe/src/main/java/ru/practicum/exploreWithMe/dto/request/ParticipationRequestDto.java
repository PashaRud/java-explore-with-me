package ru.practicum.exploreWithMe.dto.request;

import lombok.*;
import ru.practicum.exploreWithMe.enums.Status;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipationRequestDto {
        private Long id;
        private LocalDateTime created;
        @NotBlank
        private Long eventId;
        @NotBlank
        private Long requesterId;
        @NotBlank
        private Status status;
}

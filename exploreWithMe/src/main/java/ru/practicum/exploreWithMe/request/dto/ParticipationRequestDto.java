package ru.practicum.exploreWithMe.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.exploreWithMe.enums.State;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipationRequestDto {
//        private Long id;
//        private String created;
//        @NotBlank
//        private Long eventId;
//        @NotBlank
//        private Long requester;
//        @NotBlank
//        private Status status;

        private Long id;
        private Long requester;
        private Long event;
        private State status;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime created;
}

package ru.practicum.exploreWithMe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {
        private Long id;
        private String created;
        @NotBlank
        private Long eventId;
        @NotBlank
        private Long requesterId;
        @NotBlank
        private String status;
}

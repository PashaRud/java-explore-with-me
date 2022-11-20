package ru.practicum.exploreWithMe.dto.event;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUpdateEventRequest {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private boolean paid;
    private Long participantLimit;
    private boolean requestModeration;
    private String title;
}

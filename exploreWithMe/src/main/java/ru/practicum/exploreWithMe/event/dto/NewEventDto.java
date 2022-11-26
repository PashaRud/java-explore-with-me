package ru.practicum.exploreWithMe.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.practicum.exploreWithMe.location.Location;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewEventDto {
    private Long id;
    @NotBlank
    private String annotation;
    @JsonProperty("category")
    private Long category;
    @NotBlank
    private String description;
    private String eventDate;
    private Location location;
    private boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    @NotBlank
    private String title;
}

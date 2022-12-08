package ru.practicum.exploreWithMe.user.dto;

import lombok.*;
import ru.practicum.exploreWithMe.event.model.Event;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class UserDislikeDto {
    @NotBlank
    private Long id;
    @NotBlank
    private String name;
    Set<Event> dislike;
}

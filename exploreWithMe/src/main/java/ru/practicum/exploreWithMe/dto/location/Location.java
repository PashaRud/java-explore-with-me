package ru.practicum.exploreWithMe.dto.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @NotBlank
    private float lat;
    @NotBlank
    private float lon;
}

package ru.practicum.statictics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class EventViews {
    private List<ViewStats> views;
}

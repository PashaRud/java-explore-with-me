package ru.practicum.exploreWithMe.mapper.compilation;


import org.springframework.stereotype.Component;
import ru.practicum.exploreWithMe.dto.compilation.CompilationDto;
import ru.practicum.exploreWithMe.dto.compilation.NewCompilationDto;
import ru.practicum.exploreWithMe.dto.event.EventFullDto;
import ru.practicum.exploreWithMe.mapper.event.EventFullMapper;
import ru.practicum.exploreWithMe.model.Compilation;
import ru.practicum.exploreWithMe.model.Event;
import ru.practicum.exploreWithMe.service.event.EventPrivateService;
import ru.practicum.exploreWithMe.service.event.EventPublicService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class CompilationMapper {

    private EventPublicService eventService;

    public Compilation newCompilationDtoToCompilation(NewCompilationDto newCompilationDto) {

        Set<EventFullDto> eventFullDtos = newCompilationDto.getEvents().stream()
                .filter(Objects::nonNull)
                .map(eventService::getEventById)
                .collect(Collectors.toSet());

        Compilation compilation = Compilation.builder()
            .id(null)
            .title(newCompilationDto.getTitle())
            .pinned(newCompilationDto.isPinned())
            .events(eventFullDtos.stream()
                    .map(eventDto -> EventFullMapper.toEvent(eventDto))
                    .collect(Collectors.toSet()))
            .build();
        return compilation;
}

    public CompilationDto compilationToCompilationDto(Compilation compilation) {
        CompilationDto compilationDto = CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.isPinned())
                .events(compilation.getEvents().stream()
                        .map(event -> EventFullMapper.eventToEventShortDto(event))
                        .collect(Collectors.toSet()))
                .build();
        return compilationDto;
    }
}

package ru.practicum.exploreWithMe.compilation.mapper;


import org.springframework.stereotype.Component;
import ru.practicum.exploreWithMe.compilation.dto.CompilationDto;
import ru.practicum.exploreWithMe.compilation.dto.NewCompilationDto;
import ru.practicum.exploreWithMe.event.dto.EventFullDto;
import ru.practicum.exploreWithMe.event.mapper.EventFullMapper;
import ru.practicum.exploreWithMe.compilation.model.Compilation;
import ru.practicum.exploreWithMe.event.model.Event;
import ru.practicum.exploreWithMe.event.service.EventPublicService;

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
            .pinned(newCompilationDto.getPinned())
            .events(eventFullDtos.stream()
                    .map(eventDto -> EventFullMapper.toEvent(eventDto))
                    .collect(Collectors.toSet()))
            .build();
        return compilation;
}

    public static Compilation toCompilationFromNew(NewCompilationDto newCompilationDto, Set<Event> events) {
        return Compilation.builder()
                .id(newCompilationDto.getId())
                .title(newCompilationDto.getTitle())
                .pinned(newCompilationDto.getPinned())
                .events(events)
                .build();
    }

    public CompilationDto compilationToCompilationDto(Compilation compilation) {
        CompilationDto compilationDto = CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.isPinned())
                .events(compilation.getEvents())
                .build();
        return compilationDto;
    }
}

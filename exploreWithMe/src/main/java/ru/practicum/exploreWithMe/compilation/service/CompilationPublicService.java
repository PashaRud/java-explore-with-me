package ru.practicum.exploreWithMe.compilation.service;

import ru.practicum.exploreWithMe.compilation.dto.CompilationDto;

import java.util.List;

public interface CompilationPublicService {

    List<CompilationDto> getCompilations(Boolean pinned, int from, int size);

    CompilationDto getCompilationById(Long compId);
}

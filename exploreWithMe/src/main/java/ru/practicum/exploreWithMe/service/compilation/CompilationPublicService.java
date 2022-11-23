package ru.practicum.exploreWithMe.service.compilation;

import ru.practicum.exploreWithMe.dto.compilation.CompilationDto;

import java.util.List;

public interface CompilationPublicService {

    List<CompilationDto> getCompilations(Boolean pinned, int from, int size);

    CompilationDto getCompilationById(Long compId);
}

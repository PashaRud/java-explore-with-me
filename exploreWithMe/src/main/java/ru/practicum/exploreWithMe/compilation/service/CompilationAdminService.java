package ru.practicum.exploreWithMe.compilation.service;

import ru.practicum.exploreWithMe.compilation.dto.CompilationDto;
import ru.practicum.exploreWithMe.compilation.dto.NewCompilationDto;

public interface CompilationAdminService {

    CompilationDto createCompilation(NewCompilationDto dto);

    void deleteCompilation(Long compId);

    void deleteEventFromCompilation(Long compId, Long eventId);

    void addEventFromCompilation(Long compId, Long eventId);

    void unpinCompilation(Long compId);

    void pinCompilation(Long compId);
}

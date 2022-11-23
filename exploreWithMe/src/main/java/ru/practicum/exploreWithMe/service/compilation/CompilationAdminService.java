package ru.practicum.exploreWithMe.service.compilation;

import ru.practicum.exploreWithMe.dto.compilation.CompilationDto;
import ru.practicum.exploreWithMe.dto.compilation.NewCompilationDto;

public interface CompilationAdminService {

    CompilationDto createCompilation(NewCompilationDto dto);

    void deleteCompilation(Long compId);

    void deleteEventFromCompilation(Long compId, Long eventId);

    void addEventFromCompilation(Long compId, Long eventId);

    void unpinCompilation(Long compId);

    void pinCompilation(Long compId);
}

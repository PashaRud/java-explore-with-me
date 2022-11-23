package ru.practicum.exploreWithMe.controller.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.dto.compilation.CompilationDto;
import ru.practicum.exploreWithMe.dto.compilation.NewCompilationDto;
import ru.practicum.exploreWithMe.service.compilation.CompilationAdminService;

@RestController
@Slf4j
@Validated
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class CompilationAdminController {

    private final CompilationAdminService service;

    @PostMapping
    public CompilationDto createCompilation(@RequestBody NewCompilationDto dto) {
        return service.createCompilation(dto);
    }

    @DeleteMapping("{compId}")
    public void deleteCompilation(@PathVariable Long compId) {
        service.deleteCompilation(compId);
    }

    @DeleteMapping("{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable Long compId, @PathVariable Long eventId) {
        service.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("{compId}/events/{eventId}")
    public void addEventFromCompilation(@PathVariable Long compId, @PathVariable Long eventId) {
        service.addEventFromCompilation(compId, eventId);
    }

    @DeleteMapping("{compId}/pin")
    public void unpinCompilation(@PathVariable Long compId) {
        service.unpinCompilation(compId);
    }

    @PatchMapping("{compId}/pin")
    public void pinCompilation(@PathVariable Long compId) {
        service.pinCompilation(compId);
    }
}

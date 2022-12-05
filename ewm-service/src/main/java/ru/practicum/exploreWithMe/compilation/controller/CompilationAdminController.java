package ru.practicum.exploreWithMe.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.compilation.dto.CompilationDto;
import ru.practicum.exploreWithMe.compilation.dto.NewCompilationDto;
import ru.practicum.exploreWithMe.compilation.service.CompilationAdminService;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class CompilationAdminController {

    private final CompilationAdminService service;

    @PostMapping
    public CompilationDto createCompilation(@Valid @RequestBody NewCompilationDto dto) {
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

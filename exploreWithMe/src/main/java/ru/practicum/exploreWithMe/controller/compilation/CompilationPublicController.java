package ru.practicum.exploreWithMe.controller.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exploreWithMe.dto.compilation.CompilationDto;
import ru.practicum.exploreWithMe.service.compilation.CompilationPublicService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@Validated
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
public class CompilationPublicController {

    private final CompilationPublicService service;

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(name = "pinned", required = false) Boolean pinned,
                                                @RequestParam(name = "from", defaultValue = "0")
                                                @PositiveOrZero int from,
                                                @RequestParam(name = "size", defaultValue = "10")
                                                @Positive int size) {
        return service.getCompilations(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable Long compId) {
        return service.getCompilationById(compId);
    }
}

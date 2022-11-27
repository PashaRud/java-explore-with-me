package ru.practicum.exploreWithMe.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.exploreWithMe.compilation.dto.CompilationDto;
import ru.practicum.exploreWithMe.compilation.model.Compilation;
import ru.practicum.exploreWithMe.exception.NotFoundException;
import ru.practicum.exploreWithMe.compilation.repository.CompilationRepository;
import ru.practicum.exploreWithMe.utils.FromSizeRequest;

import java.util.ArrayList;
import java.util.List;

import static ru.practicum.exploreWithMe.compilation.mapper.CompilationMapper.compilationToCompilationDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationPublicServiceImpl implements CompilationPublicService {

    private final CompilationRepository repository;

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        Pageable pageable = FromSizeRequest.of(from, size);
        List<Compilation> compilations = repository.findByPinned(pinned, pageable).toList();
        List<CompilationDto> compilationDtos = new ArrayList<>();
        CompilationDto compilationDto;
        for (Compilation compilation : compilations) {
            compilationDto = compilationToCompilationDto(compilation);
            compilationDtos.add(compilationDto);
        }
        log.info("get compilation");
        return compilationDtos;
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        Compilation compilation = repository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id = " + compId + " not found"));
                        CompilationDto dto = compilationToCompilationDto(repository.findById(compId).get());
        log.info("Compilation with id = " + compId);
        return compilationToCompilationDto(compilation);
    }
}

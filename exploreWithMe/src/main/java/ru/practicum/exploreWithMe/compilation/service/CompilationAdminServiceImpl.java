package ru.practicum.exploreWithMe.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.exploreWithMe.compilation.dto.CompilationDto;
import ru.practicum.exploreWithMe.compilation.dto.NewCompilationDto;
import ru.practicum.exploreWithMe.exception.NotFoundException;
import ru.practicum.exploreWithMe.compilation.mapper.CompilationMapper;
import ru.practicum.exploreWithMe.compilation.model.Compilation;
import ru.practicum.exploreWithMe.event.model.Event;
import ru.practicum.exploreWithMe.compilation.repository.CompilationRepository;
import ru.practicum.exploreWithMe.event.repository.EventRepository;

import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class CompilationAdminServiceImpl implements CompilationAdminService {

    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public CompilationDto createCompilation(NewCompilationDto dto) {
        for (Long id :
                dto.getEvents()) {
            eventValidation(id);
        }
        CompilationDto compilationDto = compilationMapper.compilationToCompilationDto
                (compilationRepository.save(compilationMapper.newCompilationDtoToCompilation(dto)));
        log.info("Категория с id={} создана", compilationDto.getId());
        return compilationDto;
    }

    @Override
    public void deleteCompilation(Long compId) {
        compilationValidation(compId);
        compilationRepository.deleteById(compId);
    }

    @Override
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        compilationValidation(compId);
        eventValidation(eventId);
        Compilation compilation = compilationRepository.findById(compId).get();
        Set<Event> events = compilation.getEvents();
        if (events.contains(eventRepository.findById(eventId).get())) {
            events.remove(eventRepository.findById(eventId).get());
            compilation.setEvents(events);
            compilationRepository.save(compilation);
        }
    }

    @Override
    public void addEventFromCompilation(Long compId, Long eventId) {
        compilationValidation(compId);
        eventValidation(eventId);
        Compilation compilation = compilationRepository.findById(compId).get();
        Set<Event> events = compilation.getEvents();
        if (!events.contains(eventRepository.findById(eventId).get())) {
            events.add(eventRepository.findById(eventId).get());
            compilation.setEvents(events);
            compilationRepository.save(compilation);
            log.info("Event with id" + eventId + " added from compilation with id = " + compId);
        }
    }

    @Override
    public void unpinCompilation(Long compId) {
        compilationValidation(compId);
        Compilation compilation = compilationRepository.findById(compId).get();
        if (compilation.isPinned()) {
            compilation.setPinned(false);
            compilationRepository.save(compilation);
        }
    }

    @Override
    public void pinCompilation(Long compId) {
        compilationValidation(compId);
        Compilation compilation = compilationRepository.findById(compId).get();
        if (!compilation.isPinned()) {
            compilation.setPinned(true);
            compilationRepository.save(compilation);
        }
    }

    private void eventValidation(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new NotFoundException(String.format("Event with id = " + id + " not found"));
        }
    }

    private void compilationValidation(Long id) {
        if (!compilationRepository.existsById(id)) {
            throw new NotFoundException(String.format("Compilation with id = " + id + " not found"));
        }
    }
}

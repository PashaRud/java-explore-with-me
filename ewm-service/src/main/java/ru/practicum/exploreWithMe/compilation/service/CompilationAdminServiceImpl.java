package ru.practicum.exploreWithMe.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exploreWithMe.compilation.dto.CompilationDto;
import ru.practicum.exploreWithMe.compilation.dto.NewCompilationDto;
import ru.practicum.exploreWithMe.exception.NotFoundException;
import ru.practicum.exploreWithMe.compilation.model.Compilation;
import ru.practicum.exploreWithMe.event.model.Event;
import ru.practicum.exploreWithMe.compilation.repository.CompilationRepository;
import ru.practicum.exploreWithMe.event.repository.EventRepository;

import java.util.HashSet;
import java.util.Set;

import static ru.practicum.exploreWithMe.compilation.mapper.CompilationMapper.compilationToCompilationDto;
import static ru.practicum.exploreWithMe.compilation.mapper.CompilationMapper.toCompilationFromNew;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class CompilationAdminServiceImpl implements CompilationAdminService {

    private final EventRepository eventRepository;
    private final CompilationRepository compilationRepository;

    @Override
    public CompilationDto createCompilation(NewCompilationDto dto) {

        Set<Event> events = new HashSet<>(eventRepository.findAllById(dto.getEvents()));
        Compilation compilation = compilationRepository.save(toCompilationFromNew(dto, events));
        compilation.setEvents(events);
        log.info("create Compilation: " + dto);
        return compilationToCompilationDto(compilation);
    }

    @Override
    @Transactional
    public void deleteCompilation(Long compId) {
        compilationValidation(compId);
        compilationRepository.deleteById(compId);
        log.info("delete Compilation: " + compId);
    }

    @Override
    @Transactional
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        compilationValidation(compId);
        eventValidation(eventId);
        Compilation compilation = compilationRepository.findById(compId).get();
        Set<Event> events = compilation.getEvents();
        if (events.contains(eventRepository.findById(eventId).get())) {
            events.remove(eventRepository.findById(eventId).get());
            compilation.setEvents(events);
            compilationRepository.save(compilation);
            log.info("delete Event(id): " + eventId + " From Compilation(id): " + compId);
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
        log.info("unpin compilation: " + compId);
    }

    @Override
    public void pinCompilation(Long compId) {
        compilationValidation(compId);
        Compilation compilation = compilationRepository.findById(compId).get();
        if (!compilation.isPinned()) {
            compilation.setPinned(true);
            compilationRepository.save(compilation);
            log.info("pin compilation: " + compId);
        }
    }

    private void eventValidation(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new NotFoundException("Event with id = " + id + " not found");
        }
    }

    private void compilationValidation(Long id) {
        if (!compilationRepository.existsById(id)) {
            throw new NotFoundException("Compilation with id = " + id + " not found");
        }
    }
}

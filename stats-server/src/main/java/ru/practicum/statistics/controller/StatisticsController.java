package ru.practicum.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statistics.dto.EndpointHit;
import ru.practicum.statistics.dto.EventViews;
import ru.practicum.statistics.dto.ViewStats;
import ru.practicum.statistics.service.StatisticsService;


import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping
public class StatisticsController {

    private final StatisticsService service;

    @PostMapping("/hit")
    public EndpointHit addHit(@RequestBody EndpointHit endpointHit) {
        EndpointHit hit = service.addStats(endpointHit);
        log.info("Информация о запросе: " + hit.getUri() + " сохранена");
        return hit;
    }

    @GetMapping("/stats")
    public List<ViewStats> getStatistics(@RequestParam(name = "start") @NotNull String start,
                                         @RequestParam(name = "end") @NotNull String end,
                                         @RequestParam(name = "uris") List<String> uris,
                                         @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        List<ViewStats> stats = service.getStatistics(start, end, uris, unique);
        log.info("Получена статистика по посещениям");
        return stats;
    }

    @GetMapping("/views")
    public EventViews getEventViews(@RequestParam String start, @RequestParam String end,
                                    @RequestParam(required = false) List<String> uris,
                                    @RequestParam(defaultValue = "false") Boolean unique) {
        EventViews eventViews = service.getEventViews(start, end, uris, unique);
        log.info("Получена статистика по просмотрам");

        return eventViews;
    }
}

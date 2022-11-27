package ru.practicum.statictics.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.statictics.dto.EndPointHit;
import ru.practicum.statictics.dto.ViewStats;
import ru.practicum.statictics.service.StatisticsService;


import javax.validation.constraints.NotNull;
import java.util.List;


@RestController
@RequestMapping
@Slf4j
@Validated
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService service;

    @PostMapping("/hit")
    public void addHit(@RequestBody EndPointHit endpointHit) {
        service.addStats(endpointHit);
    }

    @GetMapping("/stats")
    public List<ViewStats> getStatistics(@RequestParam(name = "start") @NotNull String start,
                                         @RequestParam(name = "end") @NotNull String end,
                                         @RequestParam(name = "uris") List<String> uris,
                                         @RequestParam(name = "unique", defaultValue = "false") Boolean unique) {
        return service.getStatistics(start, end, uris, unique);
    }
}

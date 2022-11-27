package ru.practicum.statictics.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.statictics.dto.EndpointHit;
import ru.practicum.statictics.dto.ViewStats;
import ru.practicum.statictics.model.Hits;
import ru.practicum.statictics.repository.StatisticsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class EndpointHitMapper {
    private final StatisticsRepository statisticsRepository;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public EndpointHitMapper(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    public ViewStats toViewStats(Hits hits) {
        return ViewStats
                .builder()
                .app(hits.getApp())
                .uri(hits.getUri())
                .hits(statisticsRepository.findAllByAppAndUri(hits.getApp(), hits.getUri()).size())
                .build();
    }

    public Hits fromEndpointHit(EndpointHit endpointHit) {
        return Hits
                .builder()
                .id(endpointHit.getId())
                .app(endpointHit.getApp())
                .uri(endpointHit.getUri())
                .ip(endpointHit.getIp())
                .timestamp(LocalDateTime.parse(endpointHit.getTimestamp(), formatter))
                .build();
    }
}
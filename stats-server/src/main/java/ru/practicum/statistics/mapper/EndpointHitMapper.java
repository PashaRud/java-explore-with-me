package ru.practicum.statistics.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.statistics.dto.EndpointHit;
import ru.practicum.statistics.dto.ViewStats;
import ru.practicum.statistics.model.Hits;
import ru.practicum.statistics.repository.StatisticsRepository;

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

    public EndpointHit toEndpointHit(Hits hit) {
        return EndpointHit.builder()
                .uri(hit.getUri())
                .ip(hit.getIp())
                .id(hit.getId())
                .timestamp(hit.getTimestamp().format(formatter))
                .build();
    }
}
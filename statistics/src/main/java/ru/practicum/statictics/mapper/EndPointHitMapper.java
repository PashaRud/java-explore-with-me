package ru.practicum.statictics.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.statictics.dto.EndPointHit;
import ru.practicum.statictics.dto.ViewStats;
import ru.practicum.statictics.model.Hit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class EndPointHitMapper {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Hit toHit(EndPointHit endPointHit) {
        return Hit.builder()
                .id(endPointHit.getId())
                .app(endPointHit.getApp())
                .uri(endPointHit.getUri())
                .ip(endPointHit.getIp())
                .timestamp(endPointHit.getTimestamp())
                .build();
    }

    public static EndPointHit toEndPointHit(Hit hit) {
        return EndPointHit.builder()
                .id(hit.getId())
                .app(hit.getApp())
                .uri(hit.getUri())
                .ip(hit.getIp())
                .timestamp(hit.getTimestamp())
                .build();
    }

    public static ViewStats toViewStats(Hit hit) {
        return ViewStats.builder()
                .app(hit.getApp())
                .uri(hit.getUri())
                .build();
    }
}

package ru.practicum.statictics.service;

import ru.practicum.statictics.dto.EndPointHit;
import ru.practicum.statictics.dto.ViewStats;

import java.util.List;

public interface StatisticsService {

    void addStats(EndPointHit endpointHit);

    List<ViewStats> getStatistics(String start, String end, List<String> uris, boolean unique);
}

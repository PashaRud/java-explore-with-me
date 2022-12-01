package ru.practicum.statistics.service;

import ru.practicum.statistics.dto.EndpointHit;
import ru.practicum.statistics.dto.EventViews;
import ru.practicum.statistics.dto.ViewStats;

import java.util.List;

public interface StatisticsService {

    EndpointHit addStats(EndpointHit endpointHit);

    List<ViewStats> getStatistics(String start, String end, List<String> uris, boolean unique);

    EventViews getEventViews(String start, String end, List<String> uris, Boolean unique);

}

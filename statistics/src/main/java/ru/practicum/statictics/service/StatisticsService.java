package ru.practicum.statictics.service;

import ru.practicum.statictics.dto.EndpointHit;
import ru.practicum.statictics.dto.EventViews;
import ru.practicum.statictics.dto.ViewStats;

import java.util.List;

public interface StatisticsService {

    EndpointHit addStats(EndpointHit endpointHit);

    List<ViewStats> getStatistics(String start, String end, List<String> uris, boolean unique);

    EventViews getEventViews(String start, String end, List<String> uris, Boolean unique);

}

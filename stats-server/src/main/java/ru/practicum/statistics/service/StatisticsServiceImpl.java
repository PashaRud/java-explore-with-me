package ru.practicum.statistics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statistics.dto.EndpointHit;
import ru.practicum.statistics.dto.EventViews;
import ru.practicum.statistics.dto.ViewStats;
import ru.practicum.statistics.mapper.EndpointHitMapper;
import ru.practicum.statistics.model.Hits;
import ru.practicum.statistics.repository.StatisticsRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final EndpointHitMapper hitsMapper;
    private final StatisticsRepository statisticsRepository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public EndpointHit addStats(EndpointHit endPointHit) {
        Hits hit = statisticsRepository.save(hitsMapper.fromEndpointHit(endPointHit));
        return hitsMapper.toEndpointHit(hit);
    }

    @Override
    public List<ViewStats> getStatistics(String start, String end, List<String> uris, boolean unique) {
//        List<Hits> hits = statisticsRepository.findAll(
//                LocalDateTime.parse(start, formatter),
//                LocalDateTime.parse(end, formatter),
//                uris == null ? Collections.emptyList() : uris);

        List<Hits> hits = statisticsRepository.findAllByTimestampBetweenAndUriIn(LocalDateTime.parse(
                start, formatter), LocalDateTime.parse(end, formatter), uris);

        if (unique) {
            List<Hits> uniqueHits = new ArrayList<>();
            List<String> uniqueIps = hits.stream()
                    .map(Hits::getIp)
                    .distinct()
                    .collect(Collectors.toList());
            for (String ip : uniqueIps) {
                uniqueHits.add(hits.stream()
                        .filter(x -> x.getIp().equals(ip))
                        .findFirst()
                        .get());
            }
            hits = uniqueHits;
        }
        log.info("Statistics received");
        return hits.stream().map(hitsMapper::toViewStats).collect(Collectors.toList());
    }

    @Override
    public EventViews getEventViews(String start, String end, List<String> uris, Boolean unique) {
        List<ViewStats> viewStats = getStatistics(start, end, uris, unique);

        return new EventViews(viewStats);
    }
}
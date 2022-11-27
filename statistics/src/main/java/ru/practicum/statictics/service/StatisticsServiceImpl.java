package ru.practicum.statictics.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.statictics.dto.EndPointHit;
import ru.practicum.statictics.dto.ViewStats;
import ru.practicum.statictics.exception.ValidateException;
import ru.practicum.statictics.mapper.EndPointHitMapper;
import ru.practicum.statictics.repository.StatisticsRepository;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final StatisticsRepository statisticsRepository;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public void addStats(EndPointHit endPointHit) {
        statisticsRepository.save(EndPointHitMapper.toHit(endPointHit));

    }

    @Override
    public List<ViewStats> getStatistics(String start, String end, List<String> uris, boolean unique) {
        LocalDateTime startStat;
        LocalDateTime endStat;
        String app = "service";
        List<ViewStats> views = new ArrayList<>();
        ViewStats viewStats = new ViewStats(null, null, 0L);
        if (uris.isEmpty()) {
            throw new ValidateException("Uris for counting stats not passed");
        }
        try {
            startStat = LocalDateTime.parse(URLDecoder.decode(start, StandardCharsets.UTF_8.toString()), formatter);
            endStat = LocalDateTime.parse(URLDecoder.decode(end, StandardCharsets.UTF_8.toString()), formatter);
        } catch (UnsupportedEncodingException e) {
            throw new ValidateException("Time decoding error");
        }
        if (unique) {
            for (String uri :
                    uris) {
                viewStats.setUri(uri);
                viewStats.setApp(app);
                viewStats.setHits(statisticsRepository.getUniqueStatistics(startStat, endStat, uri));
                views.add(viewStats);
            }
        } else {
            for (String uri :
                    uris) {
                viewStats.setUri(uri);
                viewStats.setApp(app);
                viewStats.setHits(statisticsRepository.getStatistics(startStat, endStat, uri));
                views.add(viewStats);
            }
        }
        return views;
    }
}

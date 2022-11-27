package ru.practicum.exploreWithMe.statistics;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import ru.practicum.exploreWithMe.client.StatsClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
//
@RequiredArgsConstructor
//@Configuration
public class GetViews {

    private final StatsClient statsClient;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public Integer getViews(Long eventId) {
        ResponseEntity<Object> response = statsClient.getStats(
                LocalDateTime.of(1970, 1, 1, 0, 0, 0).format(formatter),
                LocalDateTime.now().format(formatter),
                List.of("", "/events/" + eventId, ""),
                false);
        List<LinkedHashMap<String, Object>> stats = (List<LinkedHashMap<String, Object>>) response.getBody();
        return stats.size() == 0 ? 0 : (Integer) stats.get(0).get("hits");
    }
}

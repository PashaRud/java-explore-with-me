package ru.practicum.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.statistics.model.Hits;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatisticsRepository extends JpaRepository<Hits, Long> {
    List<Hits> findAllByAppAndUri(String app, String uri);

    List<Hits> findAllByTimestampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, List<String> uris);
    @Query("select h from Hits h " +
            "where h.timestamp >= ?1 " +
            "and h.timestamp <= ?2 " +
            "and h.uri in ?3 ")
    List<Hits> findAll(LocalDateTime start, LocalDateTime end, List<String> uris);
}
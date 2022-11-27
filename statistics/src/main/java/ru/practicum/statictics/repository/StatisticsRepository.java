package ru.practicum.statictics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.statictics.model.Hit;

import java.time.LocalDateTime;

@Repository
public interface  StatisticsRepository extends JpaRepository<Hit, Long> {
    @Query(value = "SELECT COUNT(id) FROM statistics WHERE timestamp>=?1 AND timestamp<=?2 AND uri LIKE ?3", nativeQuery = true)
    Long getStatistics(LocalDateTime start, LocalDateTime end, String uri);

    @Query(value = "SELECT COUNT(id) FROM statistics WHERE timestamp>=?1 AND timestamp<=?2 AND uri LIKE ?3 ORDER BY ip", nativeQuery = true)
    Long getUniqueStatistics(LocalDateTime start, LocalDateTime end, String uri);
}

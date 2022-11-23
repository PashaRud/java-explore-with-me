package ru.practicum.exploreWithMe.repository.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.exploreWithMe.enums.Status;
import ru.practicum.exploreWithMe.model.Request;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findByEventId(Long eventId);

    List<Request> findByRequesterId(Long requesterId);

    Request findByEventIdAndRequesterId(Long eventId, Long requesterId);

    Integer countByEventIdAndStatus(Long eventId, Status status);

//    List<Request> findByEventIdAndStatus(Long eventId, Status status);
//
//    Boolean existsByEventIdAndStatus(Long eventId, Status status);
}

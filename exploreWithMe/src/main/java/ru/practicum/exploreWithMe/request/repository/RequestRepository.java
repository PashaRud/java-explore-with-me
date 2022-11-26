package ru.practicum.exploreWithMe.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.exploreWithMe.enums.Status;
import ru.practicum.exploreWithMe.request.model.Request;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findByEventId(Long eventId);

    List<Request> findByRequester(Long requester);

    Request findByEventIdAndRequester(Long eventId, Long requester);

    Integer countByEventIdAndStatus(Long eventId, Status status);

//    List<Request> findByEventIdAndStatus(Long eventId, Status status);
//
//    Boolean existsByEventIdAndStatus(Long eventId, Status status);
}

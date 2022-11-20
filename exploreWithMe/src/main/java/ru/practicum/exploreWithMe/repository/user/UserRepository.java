package ru.practicum.exploreWithMe.repository.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.exploreWithMe.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByIdIn(List<Long> ids, Pageable pageable);

    List<User> findByEmail(String email);
    User findByEventId(Long eventId);

}

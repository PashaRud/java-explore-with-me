package ru.practicum.exploreWithMe.repository.event;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.exploreWithMe.enums.State;
import ru.practicum.exploreWithMe.model.Event;
import ru.practicum.exploreWithMe.model.User;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByInitiatorId(Long userId, Pageable page);

    User findByEventId(Long eventId);

    List<Event> findByCategoryId(Long categoryId);

    @Query("SELECT e FROM Event AS e " +
            "WHERE (e.category.id IN :categories) AND " +
            "(LOWER(e.annotation) LIKE CONCAT('%',LOWER(:text),'%') OR " +
            "LOWER(e.description) LIKE CONCAT('%',LOWER(:text),'%'))")
    List<Event> findByCategoryIdsAndText(String text, List<Long> categories);

    @Query("SELECT e FROM Event AS e " +
            "WHERE (LOWER(e.annotation) LIKE CONCAT('%',LOWER(:text),'%') OR " +
            "LOWER(e.description) LIKE CONCAT('%',LOWER(:text),'%'))")
    List<Event> findByText(String text);

    @Query("SELECT e FROM Event AS e " +
            "WHERE (e.category.id IN :categories) AND " +
            "(e.initiator.id IN :users) AND " +
            "(e.state IN :states)")
    List<Event> findByUsersAndCategoriesAndStates(List<Integer> users, List<Integer> categories,
                                                  List<State> states, Pageable pageable);

    @Query("SELECT e FROM Event AS e " +
            "WHERE (e.initiator.id IN :users) AND " +
            "(e.state IN :states)")
    List<Event> findByUsersAndStates(List<Integer> users, List<State> states, Pageable pageable);

    @Query("SELECT e FROM Event AS e " +
            "WHERE (e.category.id IN :categories) AND " +
            "(e.state IN :states)")
    List<Event> findByCategoriesAndStates(List<Integer> categories, List<State> states, Pageable pageable);

    @Query("SELECT e FROM Event AS e " +
            "WHERE (e.state IN :states)")
    List<Event> findByStates(List<State> states, Pageable pageable);
}

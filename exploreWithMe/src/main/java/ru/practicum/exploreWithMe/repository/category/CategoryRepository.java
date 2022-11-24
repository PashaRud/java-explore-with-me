package ru.practicum.exploreWithMe.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.exploreWithMe.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}

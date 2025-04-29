package com.example.demo.Repository;

import com.example.demo.model.LearningCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningCardRepository extends JpaRepository<LearningCard, Long> {
    List<LearningCard> findByCategory(String category);
}

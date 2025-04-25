package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.LearningCard;
@Repository
public interface LearningCardRepository extends JpaRepository<LearningCard, Long> {
}

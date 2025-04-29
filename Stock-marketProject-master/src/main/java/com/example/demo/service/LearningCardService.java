package com.example.demo.service;

import com.example.demo.model.LearningCard;
import java.util.List;

public interface LearningCardService {
    LearningCard createCard(LearningCard card);
    LearningCard getCard(Long id);
    List<LearningCard> getCardsByCategory(String category);
    void deleteCard(Long id);
    LearningCard updateCard(LearningCard card);
}



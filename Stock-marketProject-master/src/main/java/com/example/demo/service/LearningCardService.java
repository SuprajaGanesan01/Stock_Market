package com.example.demo.service;
import java.util.List;

import com.example.demo.model.LearningCard;

public interface LearningCardService {
    List<LearningCard> getAllCards();
    LearningCard getCardById(Long id);

}



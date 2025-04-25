package com.example.demo.service;
import org.springframework.stereotype.Service;

import com.example.demo.Repository.LearningCardRepository;
import com.example.demo.model.LearningCard;

import java.util.List;

@Service
public class LearningCardServiceImpl implements LearningCardService {

    private final LearningCardRepository cardRepo;

    public LearningCardServiceImpl(LearningCardRepository cardRepo) {
        this.cardRepo = cardRepo;
    }

    @Override
    public List<LearningCard> getAllCards() {
        return cardRepo.findAll();
    }
//    public LearningCard saveLearningCard(LearningCard learningCard) {
//        return cardRepo.save(learningCard);
//    }
    
    public LearningCard getCardById(Long id) {
        return cardRepo.findById(id).orElse(null);  // Return the card by ID or null if not found
        
    }

}

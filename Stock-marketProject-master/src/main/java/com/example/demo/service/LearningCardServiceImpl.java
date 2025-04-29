package com.example.demo.service;

import com.example.demo.model.LearningCard;
import com.example.demo.Repository.LearningCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LearningCardServiceImpl implements LearningCardService {

    @Autowired
    private LearningCardRepository learningCardRepository;

    @Override
    public LearningCard createCard(LearningCard card) {
        return learningCardRepository.save(card);
    }

    @Override
    public LearningCard getCard(Long id) {
        return learningCardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Learning card not found with id: " + id));
    }

    @Override
    public List<LearningCard> getCardsByCategory(String category) {
        return learningCardRepository.findByCategory(category);
    }

    @Override
    public void deleteCard(Long id) {
        learningCardRepository.deleteById(id);
    }

    @Override
    public LearningCard updateCard(LearningCard card) {
        if (!learningCardRepository.existsById(card.getId())) {
            throw new RuntimeException("Learning card not found with id: " + card.getId());
        }
        return learningCardRepository.save(card);
    }
}

package com.example.demo.controller;

import com.example.demo.model.LearningCard;
import com.example.demo.service.LearningCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learning-cards")
@CrossOrigin(origins = "*")
public class LearningCardController {

    @Autowired
    private LearningCardService learningCardService;

    @PostMapping
    public ResponseEntity<LearningCard> createCard(@RequestBody LearningCard card) {
        return ResponseEntity.ok(learningCardService.createCard(card));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearningCard> getCard(@PathVariable Long id) {
        return ResponseEntity.ok(learningCardService.getCard(id));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<LearningCard>> getCardsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(learningCardService.getCardsByCategory(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        learningCardService.deleteCard(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<LearningCard> updateCard(@PathVariable Long id, @RequestBody LearningCard card) {
        card.setId(id);
        return ResponseEntity.ok(learningCardService.updateCard(card));
    }
} 
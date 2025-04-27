package com.example.demo.controller;

import com.example.demo.model.LearningCard;
import com.example.demo.service.EducationalContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learning")
public class EducationalContentController {

    @Autowired
    private EducationalContentService educationalContentService;

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.ok(educationalContentService.getCategories());
    }

    @GetMapping("/content/{category}")
    public ResponseEntity<List<LearningCard>> getContentByCategory(@PathVariable String category) {
        return ResponseEntity.ok(educationalContentService.getLearningContent(category));
    }

    @GetMapping("/content")
    public ResponseEntity<List<LearningCard>> getAllContent() {
        return ResponseEntity.ok(educationalContentService.getAllLearningContent());
    }

    @GetMapping("/random")
    public ResponseEntity<LearningCard> getRandomCard() {
        LearningCard card = educationalContentService.getRandomLearningCard();
        if (card != null) {
            return ResponseEntity.ok(card);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<List<LearningCard>> getContentByLevel(@PathVariable String level) {
        return ResponseEntity.ok(educationalContentService.getLearningContentByLevel(level));
    }
} 
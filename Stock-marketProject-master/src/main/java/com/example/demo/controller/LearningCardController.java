package com.example.demo.controller;


import org.springframework.web.bind.annotation.*;

import com.example.demo.model.LearningCard;
import com.example.demo.service.LearningCardService;

import java.util.List;

@RestController
@RequestMapping("/api/learning")
@CrossOrigin("*") // so React can access it
public class LearningCardController {

    private final LearningCardService cardService;

    public LearningCardController(LearningCardService cardService) {
        this.cardService = cardService;
    }
//    @PostMapping("/cards")
//    public LearningCard createLearningCard(@RequestBody LearningCard learningCard) {
//        return cardService.saveLearningCard(learningCard);
//    }
    
    // http://localhost:9090/api/learning/card?id=1
    @GetMapping("/card")
    public LearningCard getCardById(@RequestParam("id") Long id) {
        return cardService.getCardById(id);  // Fetch the card based on the ID
    }


    @GetMapping("/cards")
    public List<LearningCard> getAllCards() {
        return cardService.getAllCards();
    }
}

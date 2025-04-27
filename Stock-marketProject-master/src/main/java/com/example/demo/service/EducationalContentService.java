package com.example.demo.service;

import com.example.demo.model.LearningCard;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EducationalContentService {

    private final Map<String, List<LearningCard>> learningContent = new HashMap<>();

    public EducationalContentService() {
        initializeLearningContent();
    }

    private void initializeLearningContent() {
        // Basic Concepts
        List<LearningCard> basicConcepts = Arrays.asList(
            createLearningCard("What is a Stock?", 
                "A stock represents ownership in a company. When you buy a stock, you're buying a small piece of that company.",
                "BASIC"),
            createLearningCard("Stock Market Basics", 
                "The stock market is where shares of publicly traded companies are bought and sold.",
                "BASIC"),
            createLearningCard("Stock Price", 
                "A stock's price is determined by supply and demand in the market.",
                "BASIC")
        );
        learningContent.put("BASIC", basicConcepts);

        // Risk Management
        List<LearningCard> riskManagement = Arrays.asList(
            createLearningCard("What is Risk?", 
                "Risk in investing refers to the possibility of losing money on an investment.",
                "RISK"),
            createLearningCard("Diversification", 
                "Diversification is spreading your investments across different assets to reduce risk.",
                "RISK"),
            createLearningCard("Volatility", 
                "Volatility measures how much a stock's price fluctuates over time.",
                "RISK")
        );
        learningContent.put("RISK", riskManagement);

        // Investment Strategies
        List<LearningCard> strategies = Arrays.asList(
            createLearningCard("Long-term Investing", 
                "Long-term investing involves holding stocks for extended periods to benefit from growth.",
                "STRATEGY"),
            createLearningCard("Value Investing", 
                "Value investing involves buying stocks that appear to be trading for less than their intrinsic value.",
                "STRATEGY"),
            createLearningCard("Growth Investing", 
                "Growth investing focuses on companies that are expected to grow at an above-average rate.",
                "STRATEGY")
        );
        learningContent.put("STRATEGY", strategies);
    }

    private LearningCard createLearningCard(String title, String content, String category) {
        LearningCard card = new LearningCard();
        card.setTitle(title);
        card.setContent(content);
        card.setCategory(category);
        return card;
    }

    public List<LearningCard> getLearningContent(String category) {
        return learningContent.getOrDefault(category, new ArrayList<>());
    }

    public List<LearningCard> getAllLearningContent() {
        return learningContent.values().stream()
            .flatMap(List::stream)
            .collect(java.util.stream.Collectors.toList());
    }

    public List<String> getCategories() {
        return new ArrayList<>(learningContent.keySet());
    }

    public LearningCard getRandomLearningCard() {
        List<LearningCard> allCards = getAllLearningContent();
        if (allCards.isEmpty()) {
            return null;
        }
        int randomIndex = new Random().nextInt(allCards.size());
        return allCards.get(randomIndex);
    }

    public List<LearningCard> getLearningContentByLevel(String level) {
        return getAllLearningContent().stream()
            .filter(card -> card.getCategory().equals(level))
            .collect(java.util.stream.Collectors.toList());
    }
} 
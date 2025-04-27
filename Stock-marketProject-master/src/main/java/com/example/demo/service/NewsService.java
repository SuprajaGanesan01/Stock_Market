package com.example.demo.service;

import com.example.demo.model.News;
import java.time.LocalDateTime;
import java.util.List;

public interface NewsService {
    List<News> getRecentNews(LocalDateTime since);
    List<News> getNewsByCategory(String category);
    List<News> getNewsByStock(String stockSymbol);
    List<News> searchNews(String query);
    News saveNews(News news);
    void deleteNews(Long id);
    News getNewsById(Long id);
    void updateNewsSentiment(Long newsId, Double sentimentScore);
} 
package com.example.demo.service;

import com.example.demo.Repository.NewsRepository;
import com.example.demo.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public List<News> getRecentNews(LocalDateTime since) {
        return newsRepository.findRecentNews(since);
    }

    @Override
    public List<News> getNewsByCategory(String category) {
        return newsRepository.findByCategoryOrderByPublishedAtDesc(category);
    }

    @Override
    public List<News> getNewsByStock(String stockSymbol) {
        return newsRepository.findByRelatedStocksContainingOrderByPublishedAtDesc(stockSymbol);
    }

    @Override
    public List<News> searchNews(String query) {
        return newsRepository.findByTitleContainingOrContentContainingOrderByPublishedAtDesc(query, query);
    }

    @Override
    public News saveNews(News news) {
        return newsRepository.save(news);
    }

    @Override
    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }

    @Override
    public News getNewsById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found with id: " + id));
    }

    @Override
    public void updateNewsSentiment(Long newsId, Double sentimentScore) {
        News news = getNewsById(newsId);
        news.setSentimentScore(sentimentScore);
        newsRepository.save(news);
    }
} 
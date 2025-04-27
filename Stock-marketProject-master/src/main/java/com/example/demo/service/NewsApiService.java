package com.example.demo.service;

import com.example.demo.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsApiService {

    @Value("${news.api.key}")
    private String apiKey;

    @Value("${news.api.url}")
    private String apiUrl;

    @Autowired
    private NewsService newsService;

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(fixedRate = 3600000) // Run every hour
    public void fetchLatestNews() {
        try {
            String url = apiUrl + "?apiKey=" + apiKey + "&language=en&sortBy=publishedAt";
            ResponseEntity<NewsApiResponse> response = restTemplate.getForEntity(url, NewsApiResponse.class);
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List<News> newsList = Arrays.stream(response.getBody().getArticles())
                        .map(this::convertToNews)
                        .collect(Collectors.toList());
                
                newsList.forEach(newsService::saveNews);
            }
        } catch (Exception e) {
            // Log the error
            System.err.println("Error fetching news: " + e.getMessage());
        }
    }

    private News convertToNews(NewsApiArticle article) {
        News news = new News();
        news.setTitle(article.getTitle());
        news.setContent(article.getDescription());
        news.setSourceUrl(article.getUrl());
        news.setSourceName(article.getSource().getName());
        news.setPublishedAt(LocalDateTime.parse(article.getPublishedAt()));
        news.setCategory("General"); // Default category
        return news;
    }

    // Inner classes for API response mapping
    private static class NewsApiResponse {
        private NewsApiArticle[] articles;
        public NewsApiArticle[] getArticles() { return articles; }
        public void setArticles(NewsApiArticle[] articles) { this.articles = articles; }
    }

    private static class NewsApiArticle {
        private String title;
        private String description;
        private String url;
        private String publishedAt;
        private NewsApiSource source;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
        public String getPublishedAt() { return publishedAt; }
        public void setPublishedAt(String publishedAt) { this.publishedAt = publishedAt; }
        public NewsApiSource getSource() { return source; }
        public void setSource(NewsApiSource source) { this.source = source; }
    }

    private static class NewsApiSource {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
} 
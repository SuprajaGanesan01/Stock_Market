package com.example.demo.controller;

import com.example.demo.model.News;
import com.example.demo.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/news")
@CrossOrigin(origins = "*")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/recent")
    public ResponseEntity<List<News>> getRecentNews(
            @RequestParam(defaultValue = "24") int hours) {
        LocalDateTime since = LocalDateTime.now().minusHours(hours);
        return ResponseEntity.ok(newsService.getRecentNews(since));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<News>> getNewsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(newsService.getNewsByCategory(category));
    }

    @GetMapping("/stock/{symbol}")
    public ResponseEntity<List<News>> getNewsByStock(@PathVariable String symbol) {
        return ResponseEntity.ok(newsService.getNewsByStock(symbol));
    }

    @GetMapping("/search")
    public ResponseEntity<List<News>> searchNews(@RequestParam String query) {
        return ResponseEntity.ok(newsService.searchNews(query));
    }

    @PostMapping
    public ResponseEntity<News> createNews(@RequestBody News news) {
        return ResponseEntity.ok(newsService.saveNews(news));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.getNewsById(id));
    }

    @PutMapping("/{id}/sentiment")
    public ResponseEntity<Void> updateNewsSentiment(
            @PathVariable Long id,
            @RequestParam Double sentimentScore) {
        newsService.updateNewsSentiment(id, sentimentScore);
        return ResponseEntity.ok().build();
    }
} 
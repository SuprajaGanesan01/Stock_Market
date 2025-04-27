package com.example.demo.Repository;

import com.example.demo.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByCategoryOrderByPublishedAtDesc(String category);
    
    List<News> findByRelatedStocksContainingOrderByPublishedAtDesc(String stockSymbol);
    
    @Query("SELECT n FROM News n WHERE n.publishedAt >= ?1 ORDER BY n.publishedAt DESC")
    List<News> findRecentNews(LocalDateTime since);
    
    List<News> findByTitleContainingOrContentContainingOrderByPublishedAtDesc(String title, String content);
} 
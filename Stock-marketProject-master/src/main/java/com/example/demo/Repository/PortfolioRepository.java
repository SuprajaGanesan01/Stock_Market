package com.example.demo.Repository;

import com.example.demo.model.Portfolio;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByUser(User user);
    List<Portfolio> findByUserOrderByTotalValueDesc(User user);
    Portfolio findByNameAndUser(String name, User user);
} 
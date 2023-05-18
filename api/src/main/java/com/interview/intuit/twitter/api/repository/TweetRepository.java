package com.interview.intuit.twitter.api.repository;

import com.interview.intuit.twitter.api.model.DailySentimentRatio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TweetRepository extends JpaRepository<DailySentimentRatio, LocalDate> {
    //@Query("SELECT COUNT(t) FROM TweetEntity t WHERE t.name LIKE %:keyword% AND t.date BETWEEN :startDate AND :endDate")
    //long countByKeywordAndDateBetween(@Param("keyword") String keyword, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    //    List<TweetEntity> findByCreatedAtBetween(LocalDate start, LocalDate end);
    List<DailySentimentRatio> findByKeywordAndDateBetween(String keyword, LocalDate start, LocalDate end);
}

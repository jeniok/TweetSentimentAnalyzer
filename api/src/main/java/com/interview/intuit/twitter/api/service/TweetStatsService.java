package com.interview.intuit.twitter.api.service;

import com.interview.intuit.twitter.api.model.DailySentimentRatio;
import com.interview.intuit.twitter.api.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TweetStatsService {
    @Autowired
    private TweetRepository tweetRepository;

    public List<DailySentimentRatio> getDailySentimentRatio(String keyword, LocalDate startDate, LocalDate endDate) {
        return tweetRepository.findByKeywordAndDateBetween(keyword, startDate, endDate);
    }
}

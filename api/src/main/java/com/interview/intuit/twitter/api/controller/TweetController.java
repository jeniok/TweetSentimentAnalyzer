package com.interview.intuit.twitter.api.controller;

import com.interview.intuit.twitter.api.model.DailySentimentRatio;
import com.interview.intuit.twitter.api.service.TweetStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class TweetController {
    @Autowired
    private TweetStatsService tweetService;

    @GetMapping("/sentiments/daily")
    public ResponseEntity<List<DailySentimentRatio>> getDailySentimentRatio(
            @RequestParam(required = true) String keyword,
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<DailySentimentRatio> dailySentimentRatio = tweetService.getDailySentimentRatio(keyword, startDate, endDate);
        return ResponseEntity.ok(dailySentimentRatio);
    }
}

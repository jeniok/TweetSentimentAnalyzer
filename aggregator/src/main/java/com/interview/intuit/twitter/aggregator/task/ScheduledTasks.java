package com.interview.intuit.twitter.aggregator.task;

import com.interview.intuit.twitter.aggregator.service.DailySentimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
public class ScheduledTasks {
    @Autowired
    DailySentimentService dailySentimentService;

    @Transactional
    @Scheduled(cron = "0 0 * * * *")
    public void insertDailyAggregations() throws IOException {
        dailySentimentService.runDailySentimentRatioAggregation();
    }
}
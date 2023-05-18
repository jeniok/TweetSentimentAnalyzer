package com.interview.intuit.twitter.aggregator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DailySentimentService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SQLFileReader sqlFileReader;

    public void runDailySentimentRatioAggregation() throws IOException {
        String sql = sqlFileReader.readSqlFile("daily_sentiment_ratio_aggregation.sql");
        jdbcTemplate.execute(sql);
    }
}

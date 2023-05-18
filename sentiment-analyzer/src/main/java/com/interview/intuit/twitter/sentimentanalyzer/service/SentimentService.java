package com.interview.intuit.twitter.sentimentanalyzer.service;

import com.interview.intuit.twitter.sentimentanalyzer.model.Sentiment;

public interface SentimentService {
    Sentiment analyzeSentiment(String text);
}

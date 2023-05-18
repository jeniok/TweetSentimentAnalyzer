package com.interview.intuit.twitter.persister.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TweetEntityFactory {
    @Value("${intuit.keyword}")
    private String keyword;

    public TweetEntity createTweetEntity() {
        if ("TurboTax".equals(keyword)) {
            return new TweetEntityTurboTax();
        } else if ("QuickBooks".equals(keyword)) {
            return new TweetEntityQuickBooks();
        } else {
            throw new IllegalArgumentException("Invalid keyword type: " + keyword);
        }
    }
}

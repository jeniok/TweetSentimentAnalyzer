package com.interview.intuit.twitter.sentimentanalyzer.service;

import com.interview.intuit.twitter.common.model.TweetDTO;
import com.interview.intuit.twitter.sentimentanalyzer.model.Sentiment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TweetsConsumer {
    private static final Logger logger = LogManager.getLogger(TweetsConsumer.class);

    @Autowired
    private EnrichedTweetsProducer enrichedTweetsProducer;

    @Autowired
    private SentimentService sentimentService;

    @KafkaListener(topics = "${raw.tweets.topic}", groupId = "myGroup")
    public void consume(TweetDTO tweet) {
        logger.info("Consumed message: " + tweet);
        Sentiment sentiment = sentimentService.analyzeSentiment(tweet.getText());
        tweet.setContentRelatedScore(sentiment.getContentRelatedScore());
        tweet.setSentiment(sentiment.getSentiment());
        tweet.setSentimentScore(sentiment.getScore());
        enrichedTweetsProducer.sendMessage(tweet);
    }
}
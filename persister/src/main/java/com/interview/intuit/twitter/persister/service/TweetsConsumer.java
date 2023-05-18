package com.interview.intuit.twitter.persister.service;

import com.interview.intuit.twitter.common.model.TweetDTO;
import com.interview.intuit.twitter.persister.model.TweetEntity;
import com.interview.intuit.twitter.persister.repository.TweetRepository;
import com.interview.intuit.twitter.persister.util.TweetMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TweetsConsumer {
    private static final Logger logger = LogManager.getLogger(TweetsConsumer.class);
    @Autowired
    private TweetRepository repository;

    @Autowired
    private TweetMapper tweetMapper;

    @KafkaListener(topics = "${enriched.tweets.topic}", groupId = "myGroup")
    public void consume(TweetDTO tweet) {
        TweetEntity tweetEntity = tweetMapper.toEntity(tweet);
        logger.info("Consumed message: " + tweet);
        repository.save(tweetEntity);
    }
}

package com.interview.intuit.twitter.sentimentanalyzer.service;


import com.interview.intuit.twitter.common.model.TweetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EnrichedTweetsProducer {
    @Value("${enriched.tweets.topic}")
    private String TOPIC;

    @Autowired
    private KafkaTemplate<String, TweetDTO> kafkaTemplate;

    public void sendMessage(TweetDTO tweet) {
        this.kafkaTemplate.send(TOPIC, tweet);
    }
}

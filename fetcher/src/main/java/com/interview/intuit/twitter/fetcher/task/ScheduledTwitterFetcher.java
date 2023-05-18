package com.interview.intuit.twitter.fetcher.task;

import com.interview.intuit.twitter.common.model.TweetDTO;
import com.interview.intuit.twitter.fetcher.service.TweetsProducer;
import com.interview.intuit.twitter.fetcher.service.twitter.TwitterAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTwitterFetcher {
    @Autowired
    private TwitterAPIService twitterAPIService;

    @Autowired
    private TweetsProducer tweetsProducer;

    @Value("${fetcher.polling.rate}")
    private long rate;

    @Scheduled(fixedRateString = "${fetcher.polling.rate}")
    public void pollApi() {
        List<TweetDTO> tweets = twitterAPIService.fetchTweetsFromTwitterApi();
        for (TweetDTO tweet : tweets) {
            tweetsProducer.sendMessage(tweet);
        }
    }
}

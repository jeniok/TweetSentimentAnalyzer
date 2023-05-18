package com.interview.intuit.twitter.persister.util;

import com.interview.intuit.twitter.common.model.TweetDTO;
import com.interview.intuit.twitter.persister.model.TweetEntity;
import com.interview.intuit.twitter.persister.model.TweetEntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TweetMapper {

    @Autowired
    private TweetEntityFactory tweetEntityFactory;

    public TweetEntity toEntity(TweetDTO tweetDTO) {
        TweetEntity tweetEntity = tweetEntityFactory.createTweetEntity();
        tweetEntity.setTweetId(tweetDTO.getId());
        tweetEntity.setText(tweetDTO.getText());

        tweetEntity.setCreatedAt(tweetDTO.getCreatedAt());
        tweetEntity.setLikeCount(tweetDTO.getLikeCount());
        tweetEntity.setQuoteCount(tweetDTO.getQuoteCount());
        tweetEntity.setReplyCount(tweetDTO.getReplyCount());
        tweetEntity.setRetweetCount(tweetDTO.getRetweetCount());
        tweetEntity.setUserId(tweetDTO.getUserId());

        tweetEntity.setSentimentScore(tweetDTO.getSentimentScore());
        tweetEntity.setContentRelatedScore(tweetDTO.getContentRelatedScore());
        tweetEntity.setSentiment(tweetDTO.getSentiment());
        return tweetEntity;
    }
}

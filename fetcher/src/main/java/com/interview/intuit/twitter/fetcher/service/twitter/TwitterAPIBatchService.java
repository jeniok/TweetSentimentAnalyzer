package com.interview.intuit.twitter.fetcher.service.twitter;

import com.interview.intuit.twitter.common.model.TweetDTO;
import com.interview.intuit.twitter.fetcher.service.RedisService;
import com.twitter.clientlib.ApiException;
import com.twitter.clientlib.TwitterCredentialsBearer;
import com.twitter.clientlib.api.TwitterApi;
import com.twitter.clientlib.model.Get2TweetsSearchRecentResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;

@Service
public class TwitterAPIBatchService {
    private static final Logger logger = LogManager.getLogger(TwitterAPIBatchService.class);
    private RedisService redisService;

    @Value("${intuit.twitter.bearer.token}")
    private String twitterToken;

    @Value("${intuit.keyword}")
    private String keyword;

    private String sinceId = null;
    private String nextToken = null;

    public TwitterAPIBatchService(RedisService redisService) {
        this.redisService = redisService;
        sinceId = redisService.getKey(String.format("latestTweetIdFetched_%s", keyword));
        nextToken = redisService.getKey(String.format("nextToken_%s", keyword));
    }

    public List<TweetDTO> fetchTweetsFromTwitterApi() {
        List<TweetDTO> extractedTweets = new ArrayList<TweetDTO>();

        TwitterCredentialsBearer credentials = new TwitterCredentialsBearer(twitterToken);
        TwitterApi apiInstance = new TwitterApi(credentials);
        String query = String.format("%s -is:retweet lang:en", keyword);

        OffsetDateTime startTime = OffsetDateTime.now().minusDays(7); // OffsetDateTime | YYYY-MM-DDTHH:mm:ssZ. The oldest UTC timestamp from which the Tweets will be provided. Timestamp is in second granularity and is inclusive (i.e. 12:00:01 includes the first second of the minute).
        //OffsetDateTime endTime = OffsetDateTime.now().minusMinutes(1); // OffsetDateTime | YYYY-MM-DDTHH:mm:ssZ. The newest, most recent UTC timestamp to which the Tweets will be provided. Timestamp is in second granularity and is exclusive (i.e. 12:00:01 excludes the first second of the minute).
        String sortOrder = "recency"; // String | This order in which to return results.
        Set<String> tweetFields = new HashSet<>(Arrays.asList("attachments", "author_id", "conversation_id", "created_at", "edit_controls", "edit_history_tweet_ids", "entities", "geo", "id", "in_reply_to_user_id", "lang", "possibly_sensitive", "public_metrics", "referenced_tweets", "reply_settings", "source", "text", "withheld")); // Set<String> | A comma separated list of Tweet fields to display.
        Set<String> expansions = new HashSet<>(Arrays.asList("attachments.media_keys", "attachments.poll_ids", "author_id", "edit_history_tweet_ids", "entities.mentions.username", "geo.place_id", "in_reply_to_user_id", "referenced_tweets.id", "referenced_tweets.id.author_id")); // Set<String> | A comma separated list of fields to expand.
        //  Set<String> expansions = new HashSet<>(Arrays.asList( "author_id")); // Set<String> | A comma separated list of fields to expand.
        Set<String> mediaFields = new HashSet<>(Arrays.asList()); // Set<String> | A comma separated list of Media fields to display.
        Set<String> pollFields = new HashSet<>(Arrays.asList()); // Set<String> | A comma separated list of Poll fields to display.
        Set<String> userFields = new HashSet<>(Arrays.asList("created_at", "description", "location", "name", "pinned_tweet_id", "profile_image_url", "protected", "public_metrics", "url", "username", "verified", "withheld")); // Set<String> | A comma separated list of User fields to display.
        //Set<String> userFields = new HashSet<>(Arrays.asList("public_metrics")); // Set<String> | A comma separated list of User fields to display.
        Set<String> placeFields = new HashSet<>(Arrays.asList()); // Set<String> | A comma separated list of Place fields to display.
        try {
            Get2TweetsSearchRecentResponse result = apiInstance.tweets().tweetsRecentSearch(query)
                    .startTime(startTime)
                    //.endTime(endTime)
                    //.sinceId(sinceId)
//                    .untilId(untilId)
                    .maxResults(100)
                    .nextToken(nextToken)
                    .sortOrder(sortOrder)
                    .tweetFields(tweetFields)
                    .expansions(expansions)
                    .mediaFields(mediaFields)
                    .pollFields(pollFields)
                    .userFields(userFields)
                    .placeFields(placeFields)
                    .execute();
            logger.info(result.getMeta().getNewestId());
            nextToken = (result.getMeta().getNextToken() != null) ? result.getMeta().getNextToken() : nextToken;
            redisService.setKey(String.format("nextToken_%s", keyword), nextToken);
            if (result.getData() != null) {
                for (com.twitter.clientlib.model.Tweet tweet : result.getData()) {
                    logger.info(String.format("id: %s, createdAt: %s, text: %s", tweet.getId(), tweet.getCreatedAt().toString(), tweet.getText()));
                    var publicMetrics = tweet.getPublicMetrics();
                    extractedTweets.add(new TweetDTO(Long.parseLong(tweet.getId()), tweet.getText(), tweet.getCreatedAt(), publicMetrics.getLikeCount(), publicMetrics.getQuoteCount(),
                            publicMetrics.getReplyCount(), publicMetrics.getRetweetCount(), tweet.getAuthorId()));
                }
                logger.info("****************************************************************************************\n\n");
            }
        } catch (ApiException e) {
            logger.error("Exception when calling TweetsApi#tweetsRecentSearch");
            logger.error("Status code: " + e.getCode());
            logger.error("Reason: " + e.getResponseBody());
            logger.error("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return extractedTweets;
    }
}

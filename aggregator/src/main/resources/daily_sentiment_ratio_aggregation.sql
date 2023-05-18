WITH positive_tweets AS (
    SELECT DATE(created_at) AS date, COUNT(*) AS positive_count
    FROM tweet_entity_%s
    WHERE sentiment = 1 AND DATE(created_at) = CURRENT_DATE - INTERVAL '1 day'
    GROUP BY date
),
negative_tweets AS (
    SELECT DATE(created_at) AS date, COUNT(*) AS negative_count
    FROM tweet_entity_%s
    WHERE sentiment = 0 AND DATE(created_at) = CURRENT_DATE - INTERVAL '1 day'
    GROUP BY date
)
INSERT INTO daily_sentiment_ratio (date, keyword, positive_to_negative_ratio)
SELECT
    positive_tweets.date,
    '%s' as keyword,
    ROUND((positive_count::decimal / NULLIF(negative_count, 0)), 2) AS positive_to_negative_ratio
FROM
    positive_tweets
JOIN
    negative_tweets ON positive_tweets.date = negative_tweets.date;

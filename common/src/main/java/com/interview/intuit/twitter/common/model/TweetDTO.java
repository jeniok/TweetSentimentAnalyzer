package com.interview.intuit.twitter.common.model;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class TweetDTO {
    @NonNull
    private Long id;
    @NonNull
    private String text;

    @NonNull
    private OffsetDateTime createdAt;
    @NonNull
    private int likeCount;
    @NonNull
    private int quoteCount;
    @NonNull
    private int replyCount;
    @NonNull
    private int retweetCount;
    @NonNull
    private String userId;


    private float contentRelatedScore;
    private int sentiment;
    private float sentimentScore;
    private int sentimentModelUsed;
}

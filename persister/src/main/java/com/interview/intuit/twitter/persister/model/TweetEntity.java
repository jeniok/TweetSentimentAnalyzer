package com.interview.intuit.twitter.persister.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TweetEntity {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private Long tweetId;
    @Column(length = 1000)
    private String text;

    private OffsetDateTime createdAt;
    private int likeCount;
    private int quoteCount;
    private int replyCount;
    private int retweetCount;
    private String userId;
    private float contentRelatedScore;
    private int sentiment;
    private float sentimentScore;
}

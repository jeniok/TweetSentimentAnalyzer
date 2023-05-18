package com.interview.intuit.twitter.sentimentanalyzer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sentiment {
    private float contentRelatedScore;
    private int sentiment;
    private float score;
}

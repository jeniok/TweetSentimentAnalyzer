package com.interview.intuit.twitter.aggregator.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "daily_sentiment_ratio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailySentimentRatio {
    @Id
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "keyword")
    private String keyword;
    @Column(name = "positive_to_negative_ratio")
    private float positiveToNegativeRatio;
}

package com.interview.intuit.twitter.persister.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tweet_entity_turbotax")
public class TweetEntityTurboTax extends TweetEntity {
}
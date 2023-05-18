package com.interview.intuit.twitter.persister.repository;

import com.interview.intuit.twitter.persister.model.TweetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<TweetEntity, Long> {
}

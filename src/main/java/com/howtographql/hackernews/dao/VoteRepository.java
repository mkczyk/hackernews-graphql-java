package com.howtographql.hackernews.dao;

import com.howtographql.hackernews.graphql.type.Vote;

import java.util.List;

public interface VoteRepository {
    List<Vote> findByUserId(String userId);

    List<Vote> findByLinkId(String linkId);

    Vote saveVote(Vote vote);
}

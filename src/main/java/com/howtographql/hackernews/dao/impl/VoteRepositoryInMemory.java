package com.howtographql.hackernews.dao.impl;

import com.howtographql.hackernews.dao.VoteRepository;
import com.howtographql.hackernews.graphql.type.Vote;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VoteRepositoryInMemory implements VoteRepository {

    private final List<Vote> votes;
    private static long nextId = 0;

    public VoteRepositoryInMemory() {
        votes = new ArrayList<>();
        saveVote(new Vote(ZonedDateTime.now(), "1", "1"));
        saveVote(new Vote(ZonedDateTime.now(), "1", "2"));
    }

    @Override
    public List<Vote> findByUserId(String userId) {
        return votes.stream()
                .filter(vote -> userId.equals(vote.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Vote> findByLinkId(String linkId) {
        return votes.stream()
                .filter(vote -> linkId.equals(vote.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Vote saveVote(Vote vote) {
        nextId++;
        Vote newVote = new Vote(
                Long.toString(nextId),
                vote.getCreatedAt(),
                vote.getUserId(),
                vote.getLinkId());
        votes.add(newVote);
        return newVote;
    }
}

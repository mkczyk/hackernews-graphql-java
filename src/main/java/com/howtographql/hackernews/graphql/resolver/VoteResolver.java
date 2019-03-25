package com.howtographql.hackernews.graphql.resolver;

import com.howtographql.hackernews.dao.LinkRepository;
import com.howtographql.hackernews.dao.UserRepository;
import com.howtographql.hackernews.graphql.type.Link;
import com.howtographql.hackernews.graphql.type.User;
import com.howtographql.hackernews.graphql.type.Vote;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

public class VoteResolver {
        
    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    public VoteResolver(LinkRepository linkRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }

    @GraphQLQuery
    public User user(@GraphQLContext Vote vote) {
        return userRepository.findById(vote.getUserId());
    }

    @GraphQLQuery
    public Link link(@GraphQLContext Vote vote) {
        return linkRepository.findById(vote.getLinkId());
    }
}
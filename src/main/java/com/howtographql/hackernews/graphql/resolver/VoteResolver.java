package com.howtographql.hackernews.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.howtographql.hackernews.dao.LinkRepository;
import com.howtographql.hackernews.dao.UserRepository;
import com.howtographql.hackernews.graphql.type.Link;
import com.howtographql.hackernews.graphql.type.User;
import com.howtographql.hackernews.graphql.type.Vote;

public class VoteResolver implements GraphQLResolver<Vote> {
        
    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    public VoteResolver(LinkRepository linkRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
    }

    public User user(Vote vote) {
        return userRepository.findById(vote.getUserId());
    }
    
    public Link link(Vote vote) {
        return linkRepository.findById(vote.getLinkId());
    }
}
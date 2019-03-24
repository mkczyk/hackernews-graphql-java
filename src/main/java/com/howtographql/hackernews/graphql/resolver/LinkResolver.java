package com.howtographql.hackernews.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.howtographql.hackernews.dao.UserRepository;
import com.howtographql.hackernews.graphql.type.Link;
import com.howtographql.hackernews.graphql.type.User;

public class LinkResolver implements GraphQLResolver<Link> {
    
    private final UserRepository userRepository;

    public LinkResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User postedBy(Link link) {
        if (link.getUserId() == null) {
            return null;
        }
        return userRepository.findById(link.getUserId());
    }
}
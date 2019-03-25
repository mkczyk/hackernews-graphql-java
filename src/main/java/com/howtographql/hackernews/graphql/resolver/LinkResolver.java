package com.howtographql.hackernews.graphql.resolver;

import com.howtographql.hackernews.dao.UserRepository;
import com.howtographql.hackernews.graphql.type.Link;
import com.howtographql.hackernews.graphql.type.User;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

public class LinkResolver {

    private final UserRepository userRepository;

    public LinkResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GraphQLQuery
    public User postedBy(@GraphQLContext Link link) {
        if (link.getUserId() == null) {
            return null;
        }
        return userRepository.findById(link.getUserId());
    }
}
package com.howtographql.hackernews.graphql.resolver;

import com.howtographql.hackernews.graphql.type.SigninPayload;
import com.howtographql.hackernews.graphql.type.User;
import io.leangen.graphql.annotations.GraphQLContext;
import io.leangen.graphql.annotations.GraphQLQuery;

public class SigninResolver {

    @GraphQLQuery
    public User user(@GraphQLContext SigninPayload payload) {
        return payload.getUser();
    }
}
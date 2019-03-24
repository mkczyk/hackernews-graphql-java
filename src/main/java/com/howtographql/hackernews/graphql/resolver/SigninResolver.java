package com.howtographql.hackernews.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.howtographql.hackernews.graphql.type.SigninPayload;
import com.howtographql.hackernews.graphql.type.User;

public class SigninResolver implements GraphQLResolver<SigninPayload> {

    public User user(SigninPayload payload) {
        return payload.getUser();
    }
}
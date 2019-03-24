package com.howtographql.hackernews.dao;

import com.howtographql.hackernews.graphql.type.User;

public interface UserRepository {
    User findByEmail(String email);

    User findById(String id);

    User saveUser(User user);
}

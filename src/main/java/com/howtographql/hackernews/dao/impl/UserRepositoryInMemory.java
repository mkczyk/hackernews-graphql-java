package com.howtographql.hackernews.dao.impl;

import com.howtographql.hackernews.dao.UserRepository;
import com.howtographql.hackernews.graphql.type.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryInMemory implements UserRepository {

    private final List<User> users;
    private static long nextId = 0;

    public UserRepositoryInMemory() {
        users = new ArrayList<>();
        saveUser(new User("admin", "admin@wp.pl", "123"));
        saveUser(new User("user", "user@wp.pl", "123"));
    }

    @Override
    public User findByEmail(String email) {
        return users.stream()
                .filter(user -> email.equals(user.getEmail()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can't find user by email " + email));
    }

    @Override
    public User findById(String id) {
        return users.stream()
                .filter(user -> id.equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can't find user by id " + id));
    }

    @Override
    public User saveUser(User user) {
        nextId++;
        User newUser = new User(Long.toString(nextId), user.getName(), user.getEmail(), user.getPassword());
        users.add(newUser);
        return newUser;
    }
}

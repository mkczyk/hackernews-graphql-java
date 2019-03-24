package com.howtographql.hackernews.graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.howtographql.hackernews.dao.LinkRepository;
import com.howtographql.hackernews.dao.UserRepository;
import com.howtographql.hackernews.dao.VoteRepository;
import com.howtographql.hackernews.graphql.input.AuthData;
import com.howtographql.hackernews.graphql.type.Link;
import com.howtographql.hackernews.graphql.type.SigninPayload;
import com.howtographql.hackernews.graphql.type.User;
import com.howtographql.hackernews.graphql.type.Vote;
import graphql.GraphQLException;
import graphql.schema.DataFetchingEnvironment;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class Mutation implements GraphQLRootResolver {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;
    private final VoteRepository voteRepository;

    public Mutation(LinkRepository linkRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    //The way to inject the context is via DataFetchingEnvironment
    public Link createLink(String url, String description, DataFetchingEnvironment env) {
        AuthContext context = env.getContext();
        Link newLink = new Link(url, description, context.getUser().getId());
        linkRepository.saveLink(newLink);
        return newLink;
    }

    public User createUser(String name, AuthData auth) {
        User newUser = new User(name, auth.getEmail(), auth.getPassword());
        return userRepository.saveUser(newUser);
    }

    public SigninPayload signinUser(AuthData auth) throws IllegalAccessException {
        User user = userRepository.findByEmail(auth.getEmail());
        if (user.getPassword().equals(auth.getPassword())) {
            return new SigninPayload(user.getId(), user);
        }
        throw new GraphQLException("Invalid credentials");
    }

    public Vote createVote(String linkId, String userId) {
        ZonedDateTime now = Instant.now().atZone(ZoneOffset.UTC);
        return voteRepository.saveVote(new Vote(now, userId, linkId));
    }
}
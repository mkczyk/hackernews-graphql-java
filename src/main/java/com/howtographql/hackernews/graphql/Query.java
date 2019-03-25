package com.howtographql.hackernews.graphql;

import com.howtographql.hackernews.dao.LinkRepository;
import com.howtographql.hackernews.graphql.input.LinkFilter;
import com.howtographql.hackernews.graphql.type.Link;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;

import java.util.List;

public class Query {
    
    private final LinkRepository linkRepository;

    public Query(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @GraphQLQuery
    public List<Link> allLinks(LinkFilter filter,
                               @GraphQLArgument(name = "skip", defaultValue = "0") Number skip,
                               @GraphQLArgument(name = "first", defaultValue = "0") Number first) {
        return linkRepository.getAllLinks(filter, skip.intValue(), first.intValue());
    }
}
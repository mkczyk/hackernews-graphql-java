package com.howtographql.hackernews.graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.howtographql.hackernews.dao.LinkRepository;
import com.howtographql.hackernews.graphql.input.LinkFilter;
import com.howtographql.hackernews.graphql.type.Link;

import java.util.List;

public class Query implements GraphQLRootResolver {
    
    private final LinkRepository linkRepository;

    public Query(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> allLinks(LinkFilter filter, Number skip, Number first) {
        return linkRepository.getAllLinks(filter, skip.intValue(), first.intValue());
    }
}
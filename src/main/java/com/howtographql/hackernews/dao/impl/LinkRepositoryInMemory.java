package com.howtographql.hackernews.dao.impl;

import com.howtographql.hackernews.dao.LinkRepository;
import com.howtographql.hackernews.graphql.input.LinkFilter;
import com.howtographql.hackernews.graphql.type.Link;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LinkRepositoryInMemory implements LinkRepository {

    private final List<Link> links;
    private static long nextId = 0;

    public LinkRepositoryInMemory() {
        links = new ArrayList<>();
        saveLink(new Link("http://howtographql.com", "Your favorite GraphQL page", "1"));
        saveLink(new Link("http://graphql.org/learn/", "The official docks", "1"));
        saveLink(new Link("https://www.graph.cool", "Serverless GraphQL Backend", "1"));
    }

    @Override
    public Link findById(String id) {
        return links.stream()
                .filter(link -> id.equals(link.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can't find link by id " + id));
    }

    @Override
    public List<Link> getAllLinks(LinkFilter filter, int skip, int first) {
        Stream<Link> linkStream = links.stream()
                .filter(link -> filter == null || filter.getDescriptionContains() == null
                        || link.getDescription().toLowerCase().contains(filter.getDescriptionContains().toLowerCase()))
                .filter(link -> filter == null || filter.getUrlContains() == null
                        || link.getUrl().toLowerCase().contains(filter.getUrlContains().toLowerCase()));

        if (skip != 0 && skip != -1) {
            linkStream = linkStream.skip(skip);
        }

        if (first != 0 && first != -1) {
            linkStream = linkStream.limit(first);
        }

        return linkStream.collect(Collectors.toList());
    }


    @Override
    public void saveLink(Link link) {
        nextId++;
        links.add(new Link(Long.toString(nextId), link.getUrl(), link.getDescription(), link.getUserId()));
    }



}
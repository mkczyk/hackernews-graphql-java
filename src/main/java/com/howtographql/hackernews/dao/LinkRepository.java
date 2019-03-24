package com.howtographql.hackernews.dao;

import com.howtographql.hackernews.graphql.input.LinkFilter;
import com.howtographql.hackernews.graphql.type.Link;

import java.util.List;

public interface LinkRepository {
    Link findById(String id);

    List<Link> getAllLinks(LinkFilter filter, int skip, int first);

    void saveLink(Link link);
}

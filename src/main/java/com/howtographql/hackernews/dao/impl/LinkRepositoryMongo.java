package com.howtographql.hackernews.dao.impl;

import com.howtographql.hackernews.dao.LinkRepository;
import com.howtographql.hackernews.graphql.input.LinkFilter;
import com.howtographql.hackernews.graphql.type.Link;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.*;

public class LinkRepositoryMongo implements LinkRepository {

    private final MongoCollection<Document> links;

    public LinkRepositoryMongo(MongoCollection<Document> links) {
        this.links = links;
    }

    @Override
    public Link findById(String id) {
        Document doc = links.find(eq("_id", new ObjectId(id))).first();
        return link(doc);
    }

    @Override
    public List<Link> getAllLinks(LinkFilter filter, int skip, int first) {
        Optional<Bson> mongoFilter = Optional.ofNullable(filter).map(this::buildFilter);

        List<Link> allLinks = new ArrayList<>();
        FindIterable<Document> documents = mongoFilter.map(links::find).orElseGet(links::find);
        for (Document doc : documents.skip(skip).limit(first)) {
            allLinks.add(link(doc));
        }
        return allLinks;
    }

    private Bson buildFilter(LinkFilter filter) {
        String descriptionPattern = filter.getDescriptionContains();
        String urlPattern = filter.getUrlContains();
        Bson descriptionCondition = null;
        Bson urlCondition = null;
        if (descriptionPattern != null && !descriptionPattern.isEmpty()) {
            descriptionCondition = regex("description", ".*" + descriptionPattern + ".*", "i");
        }
        if (urlPattern != null && !urlPattern.isEmpty()) {
            urlCondition = regex("url", ".*" + urlPattern + ".*", "i");
        }
        if (descriptionCondition != null && urlCondition != null) {
            return and(descriptionCondition, urlCondition);
        }
        return descriptionCondition != null ? descriptionCondition : urlCondition;
    }

    @Override
    public void saveLink(Link link) {
        Document doc = new Document();
        doc.append("url", link.getUrl());
        doc.append("description", link.getDescription());
        doc.append("postedBy", link.getUserId());
        links.insertOne(doc);
    }

    private Link link(Document doc) {
        return new Link(
                doc.get("_id").toString(),
                doc.getString("url"),
                doc.getString("description"),
                doc.getString("postedBy"));
    }
}
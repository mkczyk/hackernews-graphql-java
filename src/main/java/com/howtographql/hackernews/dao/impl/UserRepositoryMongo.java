package com.howtographql.hackernews.dao.impl;

import com.howtographql.hackernews.dao.UserRepository;
import com.howtographql.hackernews.graphql.type.User;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;

public class UserRepositoryMongo implements UserRepository {

    private final MongoCollection<Document> users;

    public UserRepositoryMongo(MongoCollection<Document> users) {
        this.users = users;
    }

    @Override
    public User findByEmail(String email) {
        Document doc = users.find(eq("email", email)).first();
        return user(doc);
    }

    @Override
    public User findById(String id) {
        Document doc = users.find(eq("_id", new ObjectId(id))).first();
        return user(doc);
    }

    @Override
    public User saveUser(User user) {
        Document doc = new Document();
        doc.append("name", user.getName());
        doc.append("email", user.getEmail());
        doc.append("password", user.getPassword());
        users.insertOne(doc);
        return new User(
                doc.get("_id").toString(),
                user.getName(),
                user.getEmail(),
                user.getPassword());
    }
    
    private User user(Document doc) {
    if (doc == null) {
        return null;
    }
        return new User(
                doc.get("_id").toString(),
                doc.getString("name"),
                doc.getString("email"),
                doc.getString("password"));
    }
}
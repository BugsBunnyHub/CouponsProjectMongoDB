package com.linux.demo.mongo.dao;

import com.linux.demo.beans.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAO {

    @Autowired
    private MongoTemplate mongoTemplate;
    private final String collectionName = "users";

    public User getOne(ObjectId id) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        Document doc = collection.find(
            Filters.eq("_id",id)
        ).first();

        if(doc == null) throw new RuntimeException("Document is: " + doc);
        return new User(doc);
    }

    public User getOne(String username) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        Document doc = collection.find(
                Filters.eq("username",username)
        ).first();

        if(doc == null) throw new RuntimeException("Document is: " + doc);
        return new User(doc);
    }

    public boolean isExists(String username) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        long count = collection.countDocuments(
                Filters.eq("username", username)
        );

        // if count is bigger then 0 then return true, else false
        return count > 0;
    }

    public User getByCredentials(String username, String password) throws Exception {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        Document doc = collection.find(
                Filters.and(
                        Filters.eq("username", username),
                        Filters.eq("password", password)
                )
        ).first();

        if(doc == null) throw new RuntimeException("connection to Mongo might be lost");

        return new User(doc);
    }

    public User getByCredentialsEnabled(String username, String password, boolean enabled) throws Exception {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        Document doc = collection.find(
                Filters.and(
                        Filters.eq("username", username),
                        Filters.eq("password", password),
                        Filters.eq("enabled", enabled)
                )
        ).first();

        //if else operator ternary
        return doc != null ? new User(doc) : null;
    }

    public List<User> getAll() {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        List<User> docs = new ArrayList<>();
        collection.find().iterator().forEachRemaining(doc -> docs.add(new User(doc)));
        return docs;
    }

    public List<User> getAllEnabled(boolean enabled) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        List<User> docs = new ArrayList<>();
        collection.find(
                Filters.eq("enabled", enabled)
        ).iterator().forEachRemaining(doc -> docs.add(new User(doc)));
        return docs;
    }

    public boolean DeleteUser(ObjectId id) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        return collection.deleteOne(
                Filters.eq("_id", id)
        ).wasAcknowledged();
    }

    public boolean update(User user) throws Exception {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        UpdateResult res = collection.updateOne(
                Filters.eq("_id", user.getId()),
                Updates.combine(
                        Updates.set("username", user.getUsername()),
                        Updates.set("password", user.getPassword()),
                        Updates.set("role", user.getRole().name()),
                        Updates.set("enabled", user.isEnabled())
                )
        );
        return res.wasAcknowledged();
    }

    public void insertUser(User user) throws Exception {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        user.setPassword(user.getPassword());
        collection.insertOne(user.toDoc());
    }

}

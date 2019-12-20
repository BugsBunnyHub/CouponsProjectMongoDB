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

    public List<User> getAll() {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        List<User> docs = new ArrayList<>();
        collection.find().iterator().forEachRemaining(doc -> docs.add(new User(doc)));
        return docs;
    }

    public boolean DeleteUser(ObjectId id) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        return collection.deleteOne(
                Filters.eq("_id", id)
        ).wasAcknowledged();
    }

    public boolean updateUser(User user, ObjectId id) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        UpdateResult res = collection.updateOne(
                Filters.eq("_id", id),
                Updates.combine(
                        Updates.set("username", user.getUsername()),
                        Updates.set("password", user.getPassword()),
                        Updates.set("role", user.getRole())
                )
        );
        return res.wasAcknowledged();
    }

    public void insertUser(User user) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        collection.insertOne(user.toDoc());
    }

}

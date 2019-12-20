package com.linux.demo.mongo.dao;

import com.linux.demo.beans.User;
import com.linux.demo.service.PasswordEncryptor;
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

    @Autowired
    private PasswordEncryptor passwordEncryptor;

    public User getOne(ObjectId id) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        Document doc = collection.find(
            Filters.eq("_id",id)
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
                        Filters.eq("password", passwordEncryptor.encrypt(password))
                )
        ).first();

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

    public boolean update(User user, ObjectId id) throws Exception {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        UpdateResult res = collection.updateOne(
                Filters.eq("_id", id),
                Updates.combine(
                        Updates.set("username", user.getUsername()),
                        Updates.set("password", passwordEncryptor.encrypt(user.getPassword())),
                        Updates.set("role", user.getRole().name())
                )
        );
        return res.wasAcknowledged();
    }

    public void insertUser(User user) throws Exception {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        user.setPassword(passwordEncryptor.encrypt(user.getPassword()));
        collection.insertOne(user.toDoc());
    }

}

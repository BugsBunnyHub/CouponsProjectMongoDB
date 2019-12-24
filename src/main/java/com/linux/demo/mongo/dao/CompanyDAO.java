package com.linux.demo.mongo.dao;

import com.linux.demo.beans.Company;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyDAO {

    @Autowired
    private MongoTemplate mongoTemplate;
    private final String collectionName = "companies";

    public void insertCompany(Company company) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        collection.insertOne(company.toDoc());
    }

    public boolean update(Company company, ObjectId id) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        UpdateResult res = collection.updateOne(
                Filters.eq("_id", id),
                Updates.combine(
                        Updates.set("name", company.getName()),
                        Updates.set("email", company.getEmail())
                )
        );
        return res.wasAcknowledged();

    }


















}

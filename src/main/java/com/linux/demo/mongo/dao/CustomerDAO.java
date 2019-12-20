package com.linux.demo.mongo.dao;

import com.linux.demo.beans.Company;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAO {

    @Autowired
    private MongoTemplate mongoTemplate;
    private final String collectionName= "companies";

    public void insertCompany(Company company) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        collection.insertOne(company.toDoc());
    }
}

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

import java.util.ArrayList;
import java.util.List;


@Repository
public class CompanyDAO {

    @Autowired
    private MongoTemplate mongoTemplate;
    private final String collectionName = "companies";

    // add a new company
    public void insertCompany(Company company) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        collection.insertOne(company.toDoc());
    }

    // update an existing company
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

    // delete company
    public boolean DeleteCompany(ObjectId id) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        return collection.deleteOne(
                Filters.eq("_id", id)
        ).wasAcknowledged();
    }

    // boolean to check if company exists faster then getOne methods
    public boolean isExists(String name) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        long count = collection.countDocuments(
                Filters.eq("name", name)
        );

        return count > 0;
    }

    // get one company by name
    public Company getOneCompanyByName(String name) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        Document doc = collection.find(
                Filters.eq("name", name)).first();

        if (doc == null) throw new RuntimeException("Document is: " + doc);
        return new Company(doc);
    }

    // get all companies
    public List<Company> getAll() {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        List<Company> docs = new ArrayList<>();
        collection.find().iterator().forEachRemaining(doc -> docs.add(new Company(doc)));
        return docs;
    }







}

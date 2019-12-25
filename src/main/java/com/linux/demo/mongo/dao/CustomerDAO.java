package com.linux.demo.mongo.dao;

import com.linux.demo.beans.Company;
import com.linux.demo.beans.Customer;
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
public class CustomerDAO {

    @Autowired
    private MongoTemplate mongoTemplate;
    private final String collectionName = "companies";

    // add a new customer
    public void insertCustomer(Customer customer) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        collection.insertOne(customer.toDoc());
    }

    //TODO should we be able to update customer coupons this way?

    // update an existing customer
    public boolean update(Customer customer, ObjectId id) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        UpdateResult res = collection.updateOne(
                Filters.eq("_id", id),
                Updates.combine(
                        Updates.set("firstName", customer.getFirstName()),
                        Updates.set("lastName", customer.getLastName()),
                        Updates.set("email", customer.getEmail())
//                        Updates.set("coupons", customer.getCoupons())
                )
        );
        return res.wasAcknowledged();

    }

    // delete customer
    public boolean DeleteCustomer(ObjectId id) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        return collection.deleteOne(
                Filters.eq("_id", id)
        ).wasAcknowledged();
    }

    // boolean to check if customer exists faster then getOne methods
    public boolean isExists(String email) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        long count = collection.countDocuments(
                Filters.eq("email", email)
        );

        return count > 0;
    }

    // get one customer by first and last name
    public Customer getOneCustomer(String firstName, String lastName) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        Document doc = collection.find(
                Filters.and(
                        Filters.eq("firstName", firstName),
                        Filters.eq("lastName", lastName)
                )
        ).first();

        if(doc == null) throw new RuntimeException("Document is: " + doc);
        return new Customer(doc);
    }

    // get all customers
    public List<Customer> getAll() {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        List<Customer> docs = new ArrayList<>();
        collection.find().iterator().forEachRemaining(doc -> docs.add(new Customer(doc)));
        return docs;
    }

}

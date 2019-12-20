package com.linux.demo.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ReadConcern;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;

@Configuration
// this 2 beans are a must each time you config mongo DB project
public class MongoConfiguration {

    // @Value let's you withdraw info from application file
    @Value("${mongo.config.connection_string}")
    private String conStr;

    @Value("${mongo.config.db_name}")
    private String dbName;

    @Bean
    public MongoDbFactory mongoDbFactory() {
        ConnectionString connectionString = new ConnectionString(conStr);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToConnectionPoolSettings(builder -> builder.applyConnectionString(connectionString))
                .retryReads(true)
                .retryWrites(true)
                // get an OK from the server that the data reached
                .writeConcern(WriteConcern.ACKNOWLEDGED)
                // get the document that the majority of the slaves got
                .readConcern(ReadConcern.MAJORITY)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        return new SimpleMongoClientDbFactory(mongoClient, dbName);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoDbFactory());
    }

}

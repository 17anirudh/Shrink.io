package com.example.demo;

import org.bson.Document;
import org.springframework.stereotype.Component;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import io.github.cdimascio.dotenv.Dotenv;

@Component
public class Database {
    private MongoDatabase database;
    private MongoClient mongoClient;
    Dotenv dotenv = Dotenv.load();
    String DB_URL = dotenv.get("DB_URL");

    public Database() {
        System.out.println(DB_URL);
        if (DB_URL == null || DB_URL.trim().isEmpty()) {
            throw new IllegalArgumentException("Database URL cannot be null or empty");
        }
        
        System.out.println("DB URL: " + DB_URL); 
        
        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(DB_URL))
                .serverApi(serverApi)
                .build();

        try {
            mongoClient = MongoClients.create(settings); // Don't use try-with-resources here
            database = mongoClient.getDatabase("Shorters");
            database.runCommand(new Document("ping", 1));
            System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public Boolean keyExists(String key) {
        try {
            Document query = new Document("key", key);
            return database.getCollection("URL").countDocuments(query) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insertDocument(Document doc) {
        try {
            database.getCollection("URL").insertOne(doc);
            close();
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}

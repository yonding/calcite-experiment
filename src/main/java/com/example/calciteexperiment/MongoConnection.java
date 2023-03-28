package com.example.calciteexperiment;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.*;
import org.bson.Document;

public class MongoConnection {
    public void connectToMongo(){
        String uri = "mongodb+srv://uwrgoy7584:Rlarkdud5762!~!@cluster0.htn8ooq.mongodb.net/?retryWrites=true&w=majority";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("myFirstDatabase");
            System.out.println("1");
            MongoCollection<Document> collection = database.getCollection("user");
            Document doc = collection.find(eq("username", "Kayoung")).first();
            if (doc != null) {
                System.out.println(doc.toJson());
            } else {
                System.out.println("No matching documents found.");
            }
        }
    }

}
package com.fatih.db;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class AccessMongo {
	
MongoClient mongoClient;
    
    public MongoCollection getCollection(String collectionName) {
        mongoClient = new MongoClient("localhost",27017);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("ogryonsis");
        return mongoDatabase.getCollection(collectionName);
    }
    
    public void closeMongoClient(){
        mongoClient.close();
    }

}

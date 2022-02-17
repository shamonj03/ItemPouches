package dev.jresch.itempouches.storage.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.io.Closeable;
import java.io.IOException;

public class DataContext implements Closeable {

    private MongoClient client;
    private MongoDatabase database;

    public MongoDatabase connect(String ip, int port){
        String uri = "mongodb://user:pass@sample.host:27017/?maxPoolSize=20&w=majority";
        client = MongoClients.create(uri);
        database = client.getDatabase("mcserver");
        return client.getDatabase("mcserver");
    }

    @Override
    public void close() throws IOException {
        client.close();
    }
}

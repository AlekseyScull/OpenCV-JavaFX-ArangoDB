package ru.cherepanov;

import com.arangodb.*;
import com.arangodb.entity.BaseDocument;
import com.arangodb.mapping.ArangoJack;

public class DbUtil implements AutoCloseable{
    private final ArangoDB arangoDB;
    private ArangoDatabase currentDb;
    private ArangoCollection collection;
    private static DbUtil instance;

    public static DbUtil getInstance() {
        if (instance == null) {
            instance = new DbUtil();
        }
        return instance;
    }

    public ArangoDB getConnection() {
        return arangoDB;
    }

    private DbUtil() {
        arangoDB = new ArangoDB.Builder()
                .serializer(new ArangoJack())
                .build();
    }

    public ArangoDatabase getCurrentDbDb() {
        return currentDb;
    }

    public void setCurrentDb(DbName dbName) {
        currentDb = arangoDB.db(dbName);
    }

    public ArangoCollection getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = currentDb.collection(collection);
    }

    public String insertDocument(BaseDocument document) {
        String result = null;
        try {
            result = collection.insertDocument(document).getKey();
        } catch (ArangoDBException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public BaseDocument readDocument(String key) {
        BaseDocument read = collection.getDocument(key, BaseDocument.class);
        if (read != null) {
            System.out.println("Key: " + read.getKey());
            System.out.println("Attribute: " + read.getAttribute("name"));
        }
        return read;
    }

    public void close() {
        arangoDB.shutdown();
        instance = null;
    }
}

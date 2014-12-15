package com.mongodb;

import java.net.UnknownHostException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB dataBase = client.getDB("test");
        DBCollection things = dataBase.getCollection("posts");

        DBObject dbObject = things.findOne();
        System.out.println(dbObject);

    }
}

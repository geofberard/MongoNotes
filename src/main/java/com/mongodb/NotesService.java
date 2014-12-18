package com.mongodb;

import com.google.gson.Gson;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.mongodb.Note.*;

/**
 * Created by geoffreyberard on 14/12/2014.
 */
public enum NotesService {
    INSTANCE;

    public static final String DATABASE_NAME = "test";
    public static final String COLLECTION_NAME = "notes";
    public static final String FIELD_UID = "uid";

    private DBCollection notes;

    /*
    Hint: From a MongoClient, call getDB() to get a DB object, then call getCollection() to get a DBCollection object
    you can query
     */
    private NotesService() {
    }

    /*
    Get all notes from the database and map them to Note Object
    hint:
       - use find() of DBCollection and hasNext() and next() from DBCursor
       - next() return a DBObject and note need a BasicDBObject, you can use a cast
     */
    public List<Note> findAll() {
        return new ArrayList<Note>();
    }

    /*
    Add a new document in the collection
    hint:
      - use insert() of DBCollection
      - new Gson().fromJson(body, Note.class) to get a note object
     */
    public Object create(String body) {
        return new Gson().fromJson(body, Note.class);
    }

    /*
    Delete document in the collection, you must specify a query on _id {_id:...}
    hint:
      - use insert() of DBCollection
      - you can create a query with BasicDBObject or with QueryBuilder
      - you need to transform the String id to an ObjectId (new ObjectId(uid))
     */
    public Object delete(String uid) {
        return true;
    }

    /*
    Update document in the collection, you must specify a query on _id {_id:...}
    and a change {$set:....}
    hint:
      - use insert() of DBCollection
      - you can create a query with BasicDBObject or with QueryBuilder
      - you need to transform the String id to an ObjectId (new ObjectId(uid))
     */
    public Note setImportant(String uid, boolean isFavorite) {
        return new Note();
    }

}

package com.mongodb;

import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.ArrayList;
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

    private final DBCollection notes;

    private NotesService() {
        try {
            MongoClient client = new MongoClient();
            DB dataBase = client.getDB(DATABASE_NAME);
            notes = dataBase.getCollection(COLLECTION_NAME);
        } catch (UnknownHostException e) {
            throw new RuntimeException("Error during service Initialization");
        }
    }

    public List<Note> findAll() {
        List<Note> documents = new ArrayList<>();
        DBCursor dbObjects = notes.find();
        while (dbObjects.hasNext()) {
            DBObject dbObject = dbObjects.next();
            documents.add(new Note((BasicDBObject) dbObject));
        }
        return documents;
    }

    public Note find(String uid) {
        DBCursor dbObjects = notes.find(buildNoteUidQuery(uid));
        DBObject value = dbObjects.next();
        return value != null ? new Note((BasicDBObject) value) : null;
    }

    public Object create() {
        notes.insert(new BasicDBObject(KEY_TITLE, "John")
        .append(KEY_TEXT,"This is a test")
        .append(KEY_AVATAR , "../images/avatar-08.svg"));
        return true;
    }

    public Object delete(String uid) {
        notes.remove(buildNoteUidQuery(uid));
        return true;
    }

    public Note setImportant(String uid, boolean isFavorite) {
        notes.update(buildNoteUidQuery(uid), new BasicDBObject("$set", new BasicDBObject(KEY_IMPORTANT, isFavorite)));
        return find(uid);
    }

    private DBObject buildNoteUidQuery(String uid){
        return QueryBuilder.start(KEY_ID).is(new ObjectId(uid)).get();
    }
}

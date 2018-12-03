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

    private final DBCollection notes;

    private NotesService() {
        MongoClient client = new MongoClient();
        DB dataBase = client.getDB(DATABASE_NAME);
        notes = dataBase.getCollection(COLLECTION_NAME);
    }

    /*
    Get all notes from the database and map them to Note Object
    hint:
       - use find() of DBCollection and hasNext() and next() from DBCursor
       - next() return a DBObject and note need a BasicDBObject, you can use a cast
     */
    public List<Note> findAll() {
        List<Note> documents = new ArrayList<Note>();
        DBCursor dbObjects = notes.find();
        while (dbObjects.hasNext()) {
            DBObject dbObject = dbObjects.next();
            documents.add(new Note((BasicDBObject) dbObject));
        }
        documents.sort(Comparator.comparing(Note::getId).reversed());
        return documents;
    }

    public Note find(String uid) {
        DBCursor dbObjects = notes.find(buildNoteUidQuery(uid));
        DBObject value = dbObjects.next();
        return value != null ? new Note((BasicDBObject) value) : null;
    }

    /*
    Add a new document in the collection
    hint:
      - use insert() of DBCollection
      - new Gson().fromJson(body, Note.class) to get a note object
     */
    public Object create(String body) {
        Note note = new Gson().fromJson(body, Note.class);
        BasicDBObject document = new BasicDBObject(KEY_TITLE, note.getTitle())
                .append(KEY_TEXT, note.getText())
                .append(KEY_TYPE, note.getType());
        notes.insert(document);
        return find(document.getObjectId("_id").toString());
    }

    /*
    Delete document in the collection, you must specify a query on _id {_id:...}
    hint:
      - use insert() of DBCollection
      - you can create a query with BasicDBObject or with QueryBuilder
      - you need to transform the String id to an ObjectId (new ObjectId(uid))
     */
    public Object delete(String uid) {
        notes.remove(buildNoteUidQuery(uid));
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
        notes.update(buildNoteUidQuery(uid),
                new BasicDBObject("$set", new BasicDBObject(KEY_IMPORTANT, isFavorite)));
        return find(uid);
    }

    private DBObject buildNoteUidQuery(String uid) {
        return QueryBuilder.start(KEY_ID).is(new ObjectId(uid)).get();
    }
}

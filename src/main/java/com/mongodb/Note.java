package com.mongodb;

import org.bson.types.ObjectId;

/**
 * Created by geoffreyberard on 15/12/2014.
 */
public class Note {

    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_TEXT = "text";
    public static final String KEY_TYPE = "type";
    public static final String KEY_IMPORTANT = "important";

    private String id;
    private String title;
    private String text;
    private String type;
    private boolean important;

    public Note(){
        this.id = "";
        this.title = "";
        this.text = "";
        this.type = "";
        this.important = false;
    }

    public Note(String id, String title, String text, String type, boolean important) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.type = type;
        this.important = important;
    }

    public Note(BasicDBObject dbObject) {
        this.id = ((ObjectId) dbObject.get(KEY_ID)).toString();
        this.title = dbObject.getString(KEY_TITLE);
        this.text = dbObject.getString(KEY_TEXT);
        this.type = dbObject.getString(KEY_TYPE);
        this.important = dbObject.getBoolean(KEY_IMPORTANT);
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public boolean isImportant() {
        return important;
    }

    public String getType() {
        return type;
    }
}

package com.mongodb;

import org.bson.types.ObjectId;

/**
 * Created by geoffreyberard on 15/12/2014.
 */
public class Note {

    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_TEXT = "text";
    public static final String KEY_AVATAR = "avatar";
    public static final String KEY_IMPORTANT = "important";

    private String id;
    private String title;
    private String text;
    private String avatar;
    private boolean important;

    public Note(BasicDBObject dbObject) {
        this.id = ((ObjectId) dbObject.get(KEY_ID)).toString();
        this.title = dbObject.getString(KEY_TITLE);
        this.text = dbObject.getString(KEY_TEXT);
        this.avatar = dbObject.getString(KEY_AVATAR);
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

    public String getAvatar() {
        return avatar;
    }
}

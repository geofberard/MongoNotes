package com.mongodb;

import static spark.Spark.*;
import static spark.SparkBase.staticFileLocation;

/**
 * Created by geoffreyberard on 18/11/2014.
 */
public class NotesServer {

    private static final String API_CONTEXT = "/api/v1";

    public static void main(String[] args) {
        staticFileLocation("/public");
        get(API_CONTEXT + "/notes", "application/json",
                (request, response) -> NotesService.INSTANCE.findAll(),
                new JsonTransformer());

        post(API_CONTEXT + "/notes", "application/json",
                (request, response) -> {
                    return NotesService.INSTANCE.create(request.body());
                },
                new JsonTransformer());

        delete(API_CONTEXT + "/notes/:id", "application/json", (request, response) -> {
                    String uid = request.params(":id");
                    return NotesService.INSTANCE.delete(uid);
                },
                new JsonTransformer());

        get(API_CONTEXT + "/notes/set-important/:id/:isImportant", "application/json", (request, response) -> {
                    String uid = request.params(":id");
                    boolean isFavorite = Boolean.parseBoolean(request.params(":isImportant"));
                    return NotesService.INSTANCE.setImportant(uid, isFavorite);
                },
                new JsonTransformer());
    }
}

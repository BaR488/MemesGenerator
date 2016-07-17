package com.example.boris.memesgenerator.Entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by boris on 15.07.2016.
 */
@DatabaseTable(tableName = "meme")
public class Meme {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String resource;
    @DatabaseField
    private String tags;

    public void setId(int id) {
        this.id = id;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }
}

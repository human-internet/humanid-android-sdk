package com.ericjohnson.moviecatalogue.model;

public class Cast {
    String character;
    String name;
    String profilePath;

    public Cast(String character, String name, String profilePath) {
        this.character = character;
        this.name = name;
        this.profilePath = profilePath;
    }

    public String getCharacter() {
        return character;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }
}

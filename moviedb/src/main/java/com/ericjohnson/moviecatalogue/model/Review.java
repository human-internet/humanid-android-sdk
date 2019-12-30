package com.ericjohnson.moviecatalogue.model;

public class Review {
    String content;
    String author;

    public Review(String content, String author) {
        this.content = content;
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }
}
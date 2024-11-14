package com.example.library;

import java.util.Date;

public class Book {
    private String author;
    private String genre;
    private String name;
    private String imgPath;
    private boolean hasImage;

    public Book(String name, String author, String genre, String imgPath) {
        this.author = author;
        this.genre = genre;
        this.name = name;
        this.imgPath = imgPath;
        this.hasImage = imgPath != null;
    }

    public Book() {

    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getName() {
        return name;
    }

    public boolean hasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public void setName(String bookName) {
        this.name = bookName;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

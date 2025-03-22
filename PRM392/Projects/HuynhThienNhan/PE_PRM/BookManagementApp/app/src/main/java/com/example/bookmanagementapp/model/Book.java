package com.example.bookmanagementapp.model;

public class Book {
    private int id;
    private String title;
    private String publicationDate;
    private String type;
    private int authorId;

    // Getters và Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPublicationDate() { return publicationDate; }
    public void setPublicationDate(String publicationDate) { this.publicationDate = publicationDate; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public int getAuthorId() { return authorId; }
    public void setAuthorId(int authorId) { this.authorId = authorId; }
}

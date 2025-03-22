package com.example.sp25_nguyenhoangdat_net1720.entities;

public class Book {
    private int bookId;
    private String bookTitle;
    private String publishDate;
    private String type;
    private int authorId;

    public Book(String bookTitle, String publishDate, String type, int authorId) {
        this.bookTitle = bookTitle;
        this.publishDate = publishDate;
        this.type = type;
        this.authorId = authorId;
    }

    public Book() {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}

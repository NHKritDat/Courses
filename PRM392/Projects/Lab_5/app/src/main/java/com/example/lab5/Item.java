package com.example.lab5;

public class Item {
    private int image;
    private String imageUri;
    private String description;
    private String name;

    public Item(int image, String imageUri, String description, String name) {
        this.image = image;
        this.imageUri = imageUri;
        this.description = description;
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

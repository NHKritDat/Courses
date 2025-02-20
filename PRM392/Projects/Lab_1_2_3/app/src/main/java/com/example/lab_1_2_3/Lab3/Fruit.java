package com.example.lab_1_2_3.Lab3;

public class Fruit {
    private String name;
    private String description;
    private int image;
    private String imageUri;

    public Fruit(String name, String description, int image, String imageUri) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

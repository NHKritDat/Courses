package com.example.listview;

public class Fruits {
    private String name;
    private String description;
    private int imageResource;
    private String imagePath;

    public Fruits(String name, String description, int imageResource) {
        this.name = name;
        this.description = description;
        this.imageResource = imageResource;
        this.imagePath = null;
    }

    public Fruits(String name, String description, String imagePath) {
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
        this.imageResource = -1;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getImageResource() { return imageResource; }
    public String getImagePath() { return imagePath; }
}

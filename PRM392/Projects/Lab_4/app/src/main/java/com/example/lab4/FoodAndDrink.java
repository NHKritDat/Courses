package com.example.lab4;

public class FoodAndDrink {
    private final String name;
    private final String description;
    private final int price;
    private final int image;

    public FoodAndDrink(String name, String description, int price, int image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getImage() {
        return image;
    }
}

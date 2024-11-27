package edu.upc.projecte;

import java.io.Serializable;

//prova compatibilitat amb mac
public class Item implements Comparable<Item> {
    private String id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private int imageResId;

    public Item() {}

    public Item(String id, String name, String description, double price, int stock, int imageResId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.imageResId = imageResId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void decrementStock() {
        if (stock > 0) {
            stock--;
        }
    }
    public int getImageResId() {
        return imageResId;
    }


    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int compareTo(Item other) {
        return this.name.compareTo(other.name);
    }
}
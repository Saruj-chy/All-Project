package com.example.allproject.Class;

public class Product {

    private int id;
    private String food_name;
    private double price;
    private String resturant_name;
    private String rating;
    private String image;


    public Product(int id, String food_name, double price) {
        this.id = id;
        this.food_name = food_name;
        this.price = price;
    }


    public Product(int id, String food_name, double price, String resturant_name, String rating) {
        this.id = id;
        this.food_name = food_name;
        this.price = price;
        this.resturant_name = resturant_name;
        this.rating = rating;
    }

    public Product(int id, String food_name, double price, String resturant_name, String rating, String image) {
        this.id = id;
        this.food_name = food_name;
        this.price = price;
        this.resturant_name = resturant_name;
        this.rating = rating;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getResturant_name() {
        return resturant_name;
    }

    public void setResturant_name(String resturant_name) {
        this.resturant_name = resturant_name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

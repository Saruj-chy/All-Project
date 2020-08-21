package com.example.allproject.Class;

import java.util.ArrayList;
import java.util.List;

public class PostProduct {

    private String food_name;
    private String resturant_name;
    private String rating;
    private String documentId;
    private String currentId;
    private String totalPrice;
    List<String> imageList = new ArrayList<>();
    List<String> thumbList = new ArrayList<>();

    public PostProduct() {
    }

    public PostProduct(String currentId, String food_name, String resturant_name, String rating, String totalPrice, List<String> imageList, List<String> thumbList) {
        this.food_name = food_name;
        this.resturant_name = resturant_name;
        this.rating = rating;
        this.currentId = currentId;
        this.totalPrice = totalPrice;
        this.imageList = imageList;
        this.thumbList = thumbList;
    }
    public PostProduct(String documentId, String currentId, String food_name, String resturant_name, String rating, String totalPrice, List<String> imageList, List<String> thumbList) {
        this.documentId = documentId;
        this.food_name = food_name;
        this.resturant_name = resturant_name;
        this.rating = rating;
        this.currentId = currentId;
        this.totalPrice = totalPrice;
        this.imageList = imageList;
        this.thumbList = thumbList;
    }


    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getResturant_name() {
        return resturant_name;
    }

    public void setResturant_name(String resturant_name) {
        this.resturant_name = resturant_name;
    }

    public String getCurrentId() {
        return currentId;
    }

    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }


    public List<String> getThumbList() {
        return thumbList;
    }

    public void setThumbList(List<String> thumbList) {
        this.thumbList = thumbList;
    }





}

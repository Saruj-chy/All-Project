package com.example.allproject.Class;

public class Category {

    private String id;
    private String title;
    private String details ;
    private int image ;

    public Category() {
    }

    public Category(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public Category(String title, String details) {
        this.title = title;
        this.details = details;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

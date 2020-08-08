package com.example.allproject.Class;

public class Employ {

    private String id;
    private String name;
    private String position;
    private String contact;
    private String webpage;
    private String email;
    private String address;

    public Employ() {
    }

    public Employ(String id, String name, String position, String contact, String webpage, String email, String address) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.contact = contact;
        this.webpage = webpage;
        this.email = email;
        this.address = address;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getWebpage() {
        return webpage;
    }

    public void setWebpage(String webpage) {
        this.webpage = webpage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

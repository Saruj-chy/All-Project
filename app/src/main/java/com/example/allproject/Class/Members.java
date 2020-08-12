package com.example.allproject.Class;

public class Members {

    String currentId ;
    String memberFirstName ;
    String memberLastName ;
    String memberUserName ;
    String memberEmail ;
    String memberPassword;
    String memberState ;
    String memberProfileImage;

    public Members() {
    }

    public Members(String currentId, String memberFirstName, String memberLastName, String memberUserName, String memberEmail, String memberPassword, String memberState) {
        this.currentId = currentId;
        this.memberFirstName = memberFirstName;
        this.memberLastName = memberLastName;
        this.memberUserName = memberUserName;
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberState = memberState;
    }

    public Members(String currentId, String memberFirstName, String memberLastName, String memberUserName, String memberEmail, String memberPassword, String memberState, String memberProfileImage) {
        this.currentId = currentId;
        this.memberFirstName = memberFirstName;
        this.memberLastName = memberLastName;
        this.memberUserName = memberUserName;
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberState = memberState;
        this.memberProfileImage = memberProfileImage;
    }

    public String getCurrentId() {
        return currentId;
    }

    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }

    public String getMemberFirstName() {
        return memberFirstName;
    }

    public void setMemberFirstName(String memberFirstName) {
        this.memberFirstName = memberFirstName;
    }

    public String getMemberLastName() {
        return memberLastName;
    }

    public void setMemberLastName(String memberLastName) {
        this.memberLastName = memberLastName;
    }

    public String getMemberUserName() {
        return memberUserName;
    }

    public void setMemberUserName(String memberUserName) {
        this.memberUserName = memberUserName;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getMemberPassword() {
        return memberPassword;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword;
    }

    public String getMemberState() {
        return memberState;
    }

    public void setMemberState(String memberState) {
        this.memberState = memberState;
    }

    public String getMemberProfileImage() {
        return memberProfileImage;
    }

    public void setMemberProfileImage(String memberProfileImage) {
        this.memberProfileImage = memberProfileImage;
    }


}
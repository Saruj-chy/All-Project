package com.example.allproject.Class;

public class Members {

    String memberFirstName ;
    String memberLastName ;
    String memberUserName ;
    String memberEmail ;
    String memberPassword;

    public Members() {
    }

    public Members(String memberFirstName, String memberLastName, String memberUserName, String memberEmail, String memberPassword) {
        this.memberFirstName = memberFirstName;
        this.memberLastName = memberLastName;
        this.memberUserName = memberUserName;
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
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





    String memberStatus;
    String memberImage;

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    public String getMemberImage() {
        return memberImage;
    }

    public void setMemberImage(String memberImage) {
        this.memberImage = memberImage;
    }
}
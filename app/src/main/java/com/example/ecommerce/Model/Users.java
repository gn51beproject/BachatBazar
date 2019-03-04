package com.example.ecommerce.Model;

public class Users {
    private String name,password,phone,address,profileImg;

    public Users() {
    }

    public Users(String name, String password, String phone, String address, String profileImg) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.profileImg = profileImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}

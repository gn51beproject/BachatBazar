package com.example.ecommerce.Model;

public class AdminOrders {
    String address,city,date,time,name,phone,price;

    public AdminOrders() {
    }

    public AdminOrders(String address, String city, String date, String time, String name, String phone, String price) {
        this.address = address;
        this.city = city;
        this.date = date;
        this.time = time;
        this.name = name;
        this.phone = phone;
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

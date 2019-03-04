package com.example.ecommerce.Model;

public class MyOrders {

    public String date,description,discount,name,pid,price,quantity,seller,time;

    public MyOrders() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public MyOrders(String date, String description, String discount, String name, String pid, String price, String quantity, String seller, String time) {
        this.date = date;
        this.description = description;
        this.discount = discount;
        this.name = name;
        this.pid = pid;
        this.price = price;
        this.quantity = quantity;
        this.seller = seller;
        this.time = time;
    }
}

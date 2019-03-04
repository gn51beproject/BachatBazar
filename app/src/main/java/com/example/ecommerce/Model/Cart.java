package com.example.ecommerce.Model;

public class Cart {

    String date,description,discount,name,pid,price,quantity,time,seller,img;

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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Cart(String img, String date, String description, String discount, String name, String pid, String price, String quantity, String time, String seller) {
        this.date = date;
        this.description = description;
        this.discount = discount;
        this.name = name;
        this.pid = pid;
        this.price = price;
        this.quantity = quantity;
        this.time = time;
        this.seller = seller;
        this.img=img;
    }

    public Cart() {
    }
}

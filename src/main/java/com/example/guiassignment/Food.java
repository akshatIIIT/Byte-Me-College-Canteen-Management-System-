package com.example.guiassignment;

import java.io.Serializable;

public class Food implements Serializable {
    private static final long serialVersionUID = 1L; // Ensures compatibility for serialization
    private String Name;
    private double price;
    private String availability = "YES";
    private String Category;
    private String Review;

    public Food(String Name, double price, String Category) {
        this.Name = Name;
        this.price = price;
        this.Category = Category;
    }
    public String getName() {
        return this.Name;
    }
    public double getPrice() { // Customer will use it
        return this.price;
    }
    public String getAvailability() { // Admin will use it
        return this.availability;
    }
    public String getReview() { // Customer will use it
        return this.Review;
    }
    public String getCategory() {
        return this.Category;
    }
    public void setprice(double num) { // Admin will use it
        this.price = num;
    }
    public void setAvailability() { // Admin will use it
        this.availability = "NO";
    }
    public void setReview(String review) { // Customer will use it
        this.Review = review;
    }
}

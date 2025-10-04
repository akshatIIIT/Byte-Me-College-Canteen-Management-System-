package com.example.guiassignment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int orderCounter = 1;
    private int orderId;
    private Map<Food, Integer> orderItems;
    private Customer customer;
    private String status;
    private boolean isVIP;
    private String specialRequest;
    private Double total;
    private Date orderDate;
    private String deliveryAddress;
    private String contactNumber;
    private String paymentMethod;
    private String paymentDetails;
    public Order(Map<Food, Integer> orderItems, Customer customer, boolean isVIP) {
        this.orderId = orderCounter++;
        this.orderItems = orderItems;
        this.customer = customer;
        this.isVIP = isVIP;
        this.status = "pending";
        this.orderDate = new Date();
        calculateTotal();
    }
    public int getOrderId() {
        return orderId;
    }
    public ArrayList<Food> getOrderItems() {
        return new ArrayList<>(orderItems.keySet());
    }
    public boolean isVIP() {
        return isVIP;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
    public String getSpecialRequest() {
        return specialRequest;
    }
    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }
    public Double getTotal() {
        return total;
    }

    public Date getOrderDate() {
        return orderDate;
    }
    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public String getPaymentDetails() {
        return paymentDetails;
    }
    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
    public Customer getCustomer() {
        return customer;
    }
    private void calculateTotal() {
        total = 0.0;
        for (Map.Entry<Food, Integer> entry : orderItems.entrySet()) {
            Food item = entry.getKey();
            int quantity = entry.getValue();
            total += item.getPrice() * quantity;
        }
    }
    public String getOrderedItemsAsString() {
        return String.join(", ",
                orderItems.keySet().stream().map(Food::getName).toList()
        );
    }
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customer=" + customer +
                ", status='" + status + '\'' +
                ", total=" + total +
                ", orderDate=" + orderDate +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                '}';
    }
}

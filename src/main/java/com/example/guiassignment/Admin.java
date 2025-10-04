package com.example.guiassignment;
import java.io.*;
import java.util.ArrayList;
public class Admin {
    private static final String ITEMS_FILE = "globalItems.dat";
    private static final String SALES_FILE = "sales.dat";
    public ArrayList<Food> globalItem =new ArrayList<>();
    public ArrayList<Order> sales =new ArrayList<>();
    public String Name;

    public Admin(String Name) {
        this.Name = Name;
        loadItemsFromFile();
        loadSalesFromFile();
    }
    public void Add_Items(Food food){
        globalItem.add(food);
        saveItemsToFile();
    }
    public void Remove_Items(String name){
        Food foodToRemove = null;
        for (Food food : globalItem) {
            if (food.getName().equalsIgnoreCase(name)) {
                foodToRemove=food;
                break;
            }
        }
        if(foodToRemove!=null){
            globalItem.remove(foodToRemove);
            updateOrderStatusForRemovedItem(foodToRemove);
            saveItemsToFile();
            System.out.println("Item removed successfully.");
        }
        else{
            System.out.println("Item not found.");
        }
    }
    public void updateOrderStatusForRemovedItem(Food food) {
        for (Order order : sales) {
            if (order.getOrderItems().contains(food) && order.getStatus().equals("pending")) {
                order.setStatus("denied");
            }
        }
        saveSalesToFile();
    }
    public void processRefund(){
        for(Order order : sales){
            if("cancelled".equals(order.getStatus())) {
                System.out.println("Refund has been processed for order ID: "+order.getOrderId());
            }
            else{
                System.out.println("Order ID " + order.getOrderId() + " is not eligible for a refund.");
            }
        }
        saveSalesToFile();
    }
    public void Update_availability(String name) {
        Food foodToUpdate = null;
        for(Food food : globalItem) {
            if(food.getName().equalsIgnoreCase(name)) {
                foodToUpdate = food;
                break;
            }
        }
        if(foodToUpdate != null) {
            foodToUpdate.setAvailability();
            saveItemsToFile();
            System.out.println("Availability updated successfully.");
        }
        else{
            System.out.println("Item not found.");
        }
    }
    public void Modify_Price(double price, String name) {
        Food foodToUpdate = null;
        for(Food food : globalItem) {
            if(food.getName().equals(name)) {
                foodToUpdate = food;
                break;
            }
        }
        if(foodToUpdate != null){
            foodToUpdate.setprice(price);
            saveItemsToFile();
            System.out.println("Price updated successfully for " + name);
        }
        else{
            System.out.println("Item not found.");
        }
    }
    public void update_order_Status(Order order, String status) {
        order.setStatus(status);
        saveSalesToFile();
    }
    public void processOrder(Order order) {
        sales.add(order);
        saveSalesToFile();
        System.out.println("Preparing your order");
    }
    public String HandleSpecialRequest(int orderId) {
        for (Order order : sales) {
            if (order.getOrderId() == orderId) {
                return "Special Request for Order " + orderId + ": " + order.getSpecialRequest();
            }
        }
        return "Order with ID " + orderId + " not found or does not have a special request.";
    }
    public ArrayList<Order> viewPendingOrders() {
        ArrayList<Order> pendingOrders = new ArrayList<>();
        for(Order order : sales){
            if (order.getStatus().equals("pending") || order.getStatus().equals("preparing") || order.getStatus().equals("out for delivery")) {
                if (order.isVIP()) {
                    pendingOrders.add(0, order);
                }
                else{
                    pendingOrders.add(order);
                }
            }
        }
        return pendingOrders;
    }
    public void salesReport() {
        double totalSales = 0;
        int totalOrders = sales.size();
        System.out.println("Daily sales report");
        for (Order order : sales) {
            totalSales += order.getTotal();
            System.out.println("OrderID: "+order.getOrderId() + " Total INR: " + order.getTotal());
        }
        System.out.println("Total Orders: "+totalOrders);
        System.out.println("Total Sales: "+totalSales);
    }
    public void saveItemsToFile(){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ITEMS_FILE))){
            oos.writeObject(globalItem);
            System.out.println("Items saved successfully.");
        }
        catch(IOException e) {
            System.out.println("Error saving items: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public void loadItemsFromFile(){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ITEMS_FILE))){
            globalItem = (ArrayList<Food>) ois.readObject();
            System.out.println("Items loaded successfully.");
        }
        catch(IOException | ClassNotFoundException e) {
            System.out.println("Error loading items: " + e.getMessage());
        }
    }
    public void saveSalesToFile(){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SALES_FILE))){
            oos.writeObject(sales);
            System.out.println("Sales saved successfully.");
        }
        catch(IOException e) {
            System.out.println("Error saving sales: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    public void loadSalesFromFile(){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SALES_FILE))){
            sales = (ArrayList<Order>) ois.readObject();
            System.out.println("Sales loaded successfully.");
        }
        catch(IOException | ClassNotFoundException e) {
            System.out.println("Error loading sales: " + e.getMessage());
        }
    }
}

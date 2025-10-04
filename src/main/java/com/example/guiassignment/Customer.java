package com.example.guiassignment;
import java.io.*;
import java.util.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    private int customerID;
    public Map<Food, Integer> cart = new HashMap<>();
    public ArrayList<Order> history = new ArrayList<>();
    public boolean VIP = false;
    private static final String HISTORY_FILE = "order_history.txt";
    private static final String CART_FILE = "cart_data.txt";
    public Customer(int customerID) {
        this.customerID=customerID;
    }
    public int getId() {
        return this.customerID;
    }

    public void saveOrderHistory() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE))) {
            for (Order order : history) {
                writer.write("Order ID: "+order.getOrderId());
                writer.newLine();
                writer.write("Ordered Items: "+order.getOrderedItemsAsString());
                writer.newLine();
                writer.write("Total: "+order.getTotal());
                writer.newLine();
                writer.write("Status: "+order.getStatus());
                writer.newLine();
                writer.write("Order Date: "+order.getOrderDate());
                writer.newLine();
                writer.write("Delivery Address: "+order.getDeliveryAddress());
                writer.newLine();
                writer.write("Contact Number: "+order.getContactNumber());
                writer.newLine();
                writer.newLine();
            }
            System.out.println("Order history saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving order history: "+e.getMessage());
        }
    }
    public void loadOrderHistory() {
        try (BufferedReader reader=new BufferedReader(new FileReader(HISTORY_FILE))){
            history.clear();
            String line;
            while((line = reader.readLine())!=null){
                int orderId = Integer.parseInt(line.split(":")[1].trim());
                line =reader.readLine();
                String customerName = line.split(":")[1].trim();
                line =reader.readLine();
                String orderedItems = line.split(":")[1].trim();
                line =reader.readLine();
                double total = Double.parseDouble(line.split(":")[1].trim());
                line =reader.readLine();
                String status = line.split(":")[1].trim();
                line =reader.readLine();
                String orderDate = line.split(":")[1].trim();
                line =reader.readLine();
                String deliveryAddress = line.split(":")[1].trim();
                line =reader.readLine();
                String contactNumber = line.split(":")[1].trim();
                System.out.println("Loaded Order: "+orderId+" for customer: "+customerName);
                reader.readLine();
            }
            System.out.println("Order history loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading order history: "+e.getMessage());
        }
    }
    public void saveCart() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CART_FILE))) {
            for (Map.Entry<Food, Integer> entry : cart.entrySet()) {
                Food food = entry.getKey();
                int quantity = entry.getValue();
                writer.write("Food Name: " + food.getName());
                writer.newLine();
                writer.write("Price: " + food.getPrice());
                writer.newLine();
                writer.write("Quantity: " + quantity);
                writer.newLine();
                writer.newLine();
            }
            System.out.println("Cart data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving cart data: " + e.getMessage());
        }
    }
    public void loadCart() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CART_FILE))) {
            cart.clear();
            String line;
            while ((line = reader.readLine()) != null) {
                String foodName = line.split(":")[1].trim();
                line = reader.readLine();
                double price = Double.parseDouble(line.split(":")[1].trim());
                line = reader.readLine();
                int quantity = Integer.parseInt(line.split(":")[1].trim());
                line = reader.readLine();
                Food food = new Food(foodName, price, "");
                cart.put(food, quantity);
            }
            System.out.println("Cart data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading cart data: " + e.getMessage());
        }
    }
    public void Add_Items(Food food, int quantity) {
        if(quantity <= 0) {
            throw new IllegalArgumentException("quantity must be a positive value.");
        }
        cart.put(food,cart.getOrDefault(food, 0) + quantity);
        saveCart();
        System.out.println(quantity+ " "+food.getName()+" added to cart.");
    }
    public void Remove_Items(Food food) {
        if (cart.containsKey(food)) {
            cart.remove(food);
            saveCart();
            System.out.println(food.getName() + " removed from cart.");
        } else {
            System.out.println(food.getName() + " is not in the cart.");
        }
    }
    public void modifyItemQuantity(Food food, int newQuantity) {
        if(newQuantity<0){
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        if(cart.containsKey(food)){
            if (newQuantity>0){
                cart.put(food, newQuantity);
                saveCart();
                System.out.println("Updated "+food.getName()+" quantity to "+newQuantity+".");
            }
            else{
                Remove_Items(food);
            }
        }
        else {
            System.out.println(food.getName() + " is not in the cart.");
        }
    }
    public void View_all_items(Admin admin) {
        System.out.println("All Items:");
        for (Food food : admin.globalItem) {
            System.out.println("Name: "+food.getName()+" | Price: "+food.getPrice() +
                    " | Category: "+food.getCategory()+" | Availability: "+food.getAvailability());
        }
    }
    public void sort_by_price(Admin admin) {
        admin.globalItem.sort(Comparator.comparingDouble(Food::getPrice));
    }
    public void cart_total() {
        double total = 0;
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        System.out.println("Items in your cart:");
        for (Map.Entry<Food, Integer> entry : cart.entrySet()) {
            Food food = entry.getKey();
            int quantity = entry.getValue();
            double itemTotal = food.getPrice() * quantity;
            total += itemTotal;
            System.out.println("Item: "+food.getName()+", Price: "+food.getPrice() +
                    ", Quantity: " + quantity + ", Total: " + itemTotal);
        }
        System.out.println("Total Price TO PAY: " + total);
        loadCart();
    }
    public double cartTotalPrice(){
        double total=0;
        for (Map.Entry<Food, Integer> entry : cart.entrySet()) {
            Food food = entry.getKey();
            int quantity = entry.getValue();
            double itemTotal = food.getPrice() * quantity;
            total += itemTotal;
        }
        return total;
    }
    public void order_history() {
        for (Order order : this.history) {
            System.out.println("Order ID: " + order.getOrderId()+", Status: "+order.getStatus() +
                    ", Total price: " + order.getTotal());
        }
        loadOrderHistory();
    }
    public void cancel_Order(Order order) {
        if (!order.getStatus().equals("Preparing") && !order.getStatus().equals("Prepared") &&
                !order.getStatus().equals("Out for Delivery") && !order.getStatus().equals("Delivered")) {
            order.setStatus("Cancelled");
            System.out.println("order " + order.getOrderId() + " has been cancelled.");
        } else {
            System.out.println("order cannot be cancelled as it is already in progress or delivered.");
        }
    }
    public void OrderStatus(Order order) {
        System.out.println(order.getStatus());
    }
    public void ProvideReview(Food food, String feedback) {
        food.setReview(feedback);
    }
    public String ViewReview(Food food) {
        return food.getReview();
    }
    public ArrayList<Food> searchFood(Admin admin, String keyword) {
        ArrayList<Food> searchResults = new ArrayList<>();
        for(Food food : admin.globalItem){
            if (food.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                    food.getCategory().toLowerCase().contains(keyword.toLowerCase())) {
                searchResults.add(food);
            }
        }
        return searchResults;
    }
    public void checkout_process(Admin admin) {
        Scanner scanner = new Scanner(System.in);
        for(Map.Entry<Food, Integer> entry : cart.entrySet()) {
            Food food = entry.getKey();
            if(!"YES".equalsIgnoreCase(food.getAvailability())) {
                throw new IllegalArgumentException("Error: Order is not processed");
            }
        }
        System.out.println("Add special request for this order if any:");
        String req = scanner.nextLine();
        System.out.println("Checkout process started. Total amount to pay: ");
        cart_total();
        System.out.print("Enter payment method (ex Credit Card, Debit Card, UPI): ");
        String paymentMethod = scanner.nextLine();
        System.out.print("Enter payment details (Card Number/UPI ID/Cash): ");
        String paymentDetails = scanner.nextLine();
        System.out.print("Enter delivery address: ");
        String deliveryAddress = scanner.nextLine();
        System.out.print("Enter contact number for delivery: ");
        String contactNumber = scanner.nextLine();
        System.out.println("Processing payment through " + paymentMethod + "...");
        System.out.println("Payment successful!");
        Order newOrder = new Order(new HashMap<>(cart), this, VIP);
        newOrder.setDeliveryAddress(deliveryAddress);
        newOrder.setContactNumber(contactNumber);
        newOrder.setPaymentMethod(paymentMethod);
        newOrder.setPaymentDetails(paymentDetails);
        newOrder.setSpecialRequest(req);
        history.add(newOrder);
        saveOrderHistory();
        cart.clear();
        saveCart();
        admin.processOrder(newOrder);
        System.out.println("Order "+newOrder.getOrderId()+" placed successfully. It will be delivered to "+deliveryAddress+".");
    }
    public ArrayList<Food> Filter_By_Category(Admin admin, String category) {
        ArrayList<Food> filteredItems = new ArrayList<>();
        for(Food food : admin.globalItem){
            if(food.getCategory().equalsIgnoreCase(category)) {
                filteredItems.add(food);
            }
        }
        if(filteredItems.isEmpty()){
            System.out.println("No items found in the " + category + " category.");
        }
        return filteredItems;
    }
    public Map<Food, Integer> getCart() {
        return cart;
    }
}

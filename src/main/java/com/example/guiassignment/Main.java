package com.example.guiassignment;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Main {
    private static Map<String, Customer> customers = new HashMap<>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = null;
        boolean appRunning=true;
        while (appRunning){
            System.out.println("\nSelect User:");
            System.out.println("1. Admin");
            System.out.println("2. Customer");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int mainChoice = scanner.nextInt();
            scanner.nextLine();
            switch(mainChoice){
                case 1->{
                    if(admin == null){
                        System.out.print("Enter Admin Name: ");
                        String name = scanner.nextLine();
                        admin = new Admin(name);
                    }
                    adminInterface(scanner, admin);
                }
                case 2 -> {
                    System.out.print("Enter Customer ID: ");
                    int customerId=scanner.nextInt();
                    boolean isVIP=false;
                    Customer customer=customers.get(customerId);
                    if (customer!=null) {
                        System.out.println("Welcome back, "+customerId+"!");
                    }
                    else{
                        customer=new Customer(customerId);
                        customers.put(String.valueOf(customerId), customer);
                        System.out.println("New customer profile created for " + customerId);
                    }
                    customerInterface(scanner, customer, admin);
                }
                case 3 ->{
                    System.out.println("Exiting application...");
                    appRunning = false;
                }
                default ->System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public static void adminInterface(Scanner scanner, Admin admin) {
        boolean adminRunning = true;
        while(adminRunning){
            System.out.println("\nAdmin Interface");
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Update Availability");
            System.out.println("4. Modify Price");
            System.out.println("5. View Pending Orders");
            System.out.println("6. Handle Special Request");
            System.out.println("7. Updating Order Status");
            System.out.println("8. Process Refund");
            System.out.println("9. Generate Sales Report");
            System.out.println("10. Return to Main Menu");
            System.out.print("Enter choice: ");
            int adminChoice = scanner.nextInt();
            scanner.nextLine();
            switch(adminChoice){
                case 1 ->{
                    System.out.print("Enter Food Name: ");
                    String foodName = scanner.nextLine();
                    System.out.print("Enter Price: ");
                    int price = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Category : ");
                    String category = scanner.nextLine();
                    Food food = new Food(foodName, price, category);
                    admin.Add_Items(food);
                    System.out.println("Item added successfully.");
                }
                case 2 ->{
                    System.out.print("Enter Food Name to Remove: ");
                    String foodName = scanner.nextLine();
                    admin.Remove_Items(foodName);
                }
                case 3 ->{
                    System.out.print("Enter Food Name to Update Availability: ");
                    String foodName = scanner.nextLine();
                    admin.Update_availability(foodName);
                }
                case 4 ->{
                    System.out.print("Enter Food Name to Modify Price: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter the price ");
                    double price = scanner.nextDouble();
                    admin.Modify_Price(price, name);
                }
                case 5 ->{
                    ArrayList<Order> pendingOrders = admin.viewPendingOrders();
                    System.out.println("Pending Orders:");
                    for(Order order : pendingOrders){
                        System.out.println("Order ID: "+order.getOrderId()+" | Total: "+order.getTotal());
                    }
                }
                case 6 ->{
                    System.out.print("Enter Order ID for Special Request: ");
                    int orderId = scanner.nextInt();
                    String special = admin.HandleSpecialRequest(orderId);
                    System.out.println(special);
                }
                case 7 ->{
                    System.out.print("Enter the current status");
                    String status=scanner.nextLine();
                    System.out.println("Enter order id");
                    int orderId = scanner.nextInt();
                    for(Order order :admin.sales){
                        if(order.getOrderId()==orderId){
                            admin.update_order_Status(order,status);
                        }
                    }
                    System.out.println("Status Updated");
                }
                case 8 ->{
                    System.out.println("Refund process started");
                    admin.processRefund();
                }
                case 9 ->admin.salesReport();
                case 10 -> adminRunning = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    public static void customerInterface(Scanner scanner, Customer customer, Admin admin) {
        boolean customerRunning = true;
        while (customerRunning) {
            System.out.println("\nCustomer Interface");
            System.out.println("1. Get Premium");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. Remove Item from Cart");
            System.out.println("4. Modify Quantities");
            System.out.println("5. View All Items");
            System.out.println("6. Sort Cart by Price");
            System.out.println("7. View Cart");
            System.out.println("8. Checkout");
            System.out.println("9. Filter Items by Category");
            System.out.println("10. View Review of an Item");
            System.out.println("11. Provide Review for an Item");
            System.out.println("12. Search for Food Item");
            System.out.println("13. Order History");
            System.out.println("14. Cancel Order");
            System.out.println("15. Order Status");
            System.out.println("16. Cart_TotalPrice");
            System.out.println("17. Return to Main Menu");
            System.out.print("Enter choice: ");
            int customerChoice = scanner.nextInt();
            scanner.nextLine();
            switch(customerChoice){
                case 1 ->{
                    System.out.println("Thankyou for purchasing Premium Mode");
                    customer.VIP=true;
                }
                case 2 -> {
                    System.out.print("Enter Food Name to Add: ");
                    String foodName = scanner.nextLine();
                    System.out.println("Enter its quantity");
                    int quant=scanner.nextInt();
                    Food foodToAdd = null;
                    for(Food food:admin.globalItem){
                        if(food.getName().equals(foodName)){
                            foodToAdd = food;
                            break;
                        }
                    }
                    if(foodToAdd != null){
                        customer.Add_Items(foodToAdd,quant);
                        System.out.println("Item added to cart.");
                    }
                    else{
                        System.out.println("Item not found in available items.");
                    }
                }
                case 3 ->{
                    System.out.print("Enter Food Name to Remove: ");
                    String foodName = scanner.nextLine();
                    Food foodToRemove = null;
                    for(Food food : customer.cart.keySet()){
                        if(food.getName().equals(foodName)){
                            foodToRemove = food;
                            break;
                        }
                    }
                    if(foodToRemove!=null){
                        customer.Remove_Items(foodToRemove);
                        System.out.println(foodName + " removed from the cart.");
                    }
                    else{
                        System.out.println("Item not found in the cart.");
                    }
                }
                case 4 ->{
                    System.out.print("Enter Food Name to Modify Quantity: ");
                    String foodName=scanner.nextLine();
                    Food foodToModify=null;
                    for(Food food:customer.cart.keySet()){
                        if(food.getName().equals(foodName)){
                            foodToModify=food;
                            break;
                        }
                    }
                    if(foodToModify != null){
                        System.out.print("Enter new quantity for "+foodName+": ");
                        int newQuantity=scanner.nextInt();
                        scanner.nextLine();
                        customer.modifyItemQuantity(foodToModify, newQuantity);
                    }
                    else{
                        System.out.println(foodName + " is not in the cart.");
                    }
                }
                case 5 ->{
                    System.out.println("All Items:");
                    customer.View_all_items(admin);
                }
                case 6 ->{
                    customer.sort_by_price(admin);
                    System.out.println("Cart sorted by price.");
                }
                case 7 -> customer.cart_total();
                case 8 -> customer.checkout_process(admin);
                case 9 ->{
                    System.out.print("Enter category to filter (Veg/Non-Veg): ");
                    String category = scanner.nextLine();
                    ArrayList<Food> filteredItems = customer.Filter_By_Category(admin, category);
                    for(Food food:filteredItems){
                        System.out.println("Name: " + food.getName());
                    }
                }
                case 10 ->{
                    System.out.print("Enter Food Name to view review: ");
                    String foodName=scanner.nextLine();
                    Food food=null;
                    for(Food item:admin.globalItem){
                        if(item.getName().equals(foodName)) {
                            food=item;
                            break;
                        }
                    }
                    if(food!=null){
                        System.out.println("Review for "+foodName+": "+customer.ViewReview(food));
                    }
                    else{
                        System.out.println("Item not found.");
                    }
                }
                case 11 ->{
                    System.out.print("Enter Food Name to provide review: ");
                    String foodName=scanner.nextLine();
                    Food food=null;
                    for(Food item:admin.globalItem){
                        if(item.getName().equals(foodName)){
                            food=item;
                            break;
                        }
                    }
                    if(food!=null){
                        System.out.print("Enter your review: ");
                        String review = scanner.nextLine();
                        customer.ProvideReview(food, review);
                        System.out.println("Review added for "+foodName);
                    }
                    else{
                        System.out.println("Item not found.");
                    }
                }
                case 12 ->{
                    System.out.print("Enter keyword to search for food: ");
                    String keyword=scanner.nextLine();
                    ArrayList<Food> searchResults=customer.searchFood(admin, keyword);
                    System.out.println("Search Results:");
                    for(Food food : searchResults){
                        System.out.println("Name: "+food.getName()+" | Price: "+food.getPrice());
                    }
                }
                case 13 ->{
                    System.out.println("Order History:");
                    customer.order_history();
                }
                case 14 ->{
                    System.out.print("Enter Order ID to Cancel: ");
                    int orderId = scanner.nextInt();
                    scanner.nextLine();
                    Order OrderToCancel=null;
                    for(Order order:customer.history){
                        if(order.getOrderId()==orderId){
                            OrderToCancel = order;
                            break;
                        }
                    }
                    if(OrderToCancel!=null) {
                        customer.cancel_Order(OrderToCancel);
                    }
                    else{
                        System.out.println("Order " + orderId + "not found");
                    }
                }
                case 15 ->{
                    System.out.print("Enter Order ID for status ");
                    int orderID=scanner.nextInt();
                    scanner.nextLine();
                    for(Order order:customer.history){
                        if(order.getOrderId()==orderID){
                            customer.OrderStatus(order);
                            break;
                        }
                    }
                }
                case 16 ->{
                    System.out.print("Total price for the current cart is "+ customer.cartTotalPrice());

                }
                case 17 -> customerRunning = false;
                default ->System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

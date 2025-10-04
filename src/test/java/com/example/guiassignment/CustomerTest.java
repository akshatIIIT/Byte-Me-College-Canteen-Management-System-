package com.example.guiassignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

@Nested
class CustomerTest {
    private Customer customer;
    private Food item1;
    private Food item2;

    @BeforeEach
    void setUp() {
        customer=new Customer(1); // taking random customer id just for eg;
        item1=new Food("pizza", 100, "Veg");
        item2=new Food("Burger", 50, "Non-Veg");
    }

    @Test
    void testAdd_Items() {
        customer.Add_Items(item1, 10);
        customer.Add_Items(item1, 20);
        double expected_total = item1.getPrice() * 30;
        double total = customer.cartTotalPrice();
        assertEquals(expected_total, total, "Total price should match after adding an item.");
    }

    @Test
    void testmodifyitemquantity() {
        customer.Add_Items(item1, 20);
        customer.Add_Items(item2, 30);
        customer.modifyItemQuantity(item1, 50); // i am update quantity to 50
        double expected_total = (item1.getPrice() * 50) + (item2.getPrice() * 30);
        double total = customer.getCart().entrySet().stream().mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue()).sum();
        assertEquals(expected_total, total, "Total price should match after modifying item quantity.");
    }
    @Test
    public void testOrderOutOfStockItem() {
        item1.setAvailability();
        customer.Add_Items(item1, 1);
        Admin adminSaheb = new Admin("adminSaheb");
        assertThrows(IllegalArgumentException.class, () -> {  // i am checking that whether checkout_process throws an exception when an out-of-stock item is in the cart
            customer.checkout_process(adminSaheb);
        }, " prevent the checkout process for out-of-stock items.");
    }
    @Test
    public void testPrevent_negative_quantityinCart() {
        assertThrows(IllegalArgumentException.class, () -> {
            customer.Add_Items(item1, -10);
        }, " prevent setting a negative quantity.");
    }
}

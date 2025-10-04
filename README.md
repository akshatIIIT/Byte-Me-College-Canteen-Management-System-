🍔 Byte Me! – College Canteen Ordering System

“Byte Me!” is a food ordering system designed specifically for our college canteen. It provides both a CLI-based application and a JavaFX-based GUI interface.

The system helps students browse menus, place orders, and track deliveries, while also enabling canteen staff to manage menu items and process orders efficiently.

📌 Assumptions

There is only 1 Admin to handle the canteen and its menu.

Customers have unique CustomerIDs.

A customer can buy Premium and upgrade to VIP.

A customer cannot cancel an order if its status is Preparing, Prepared, or Out-for-delivery.

Authentication functionality was optional in Assignment 4, so alternative options have been implemented.

⚙️ Application Structure

The system contains 4 core classes:

Admin

Order

Food

Customer

Key OOP Concepts Implemented

Encapsulation: Classes have public/private attributes accessed via getter and setter methods.

Relations: Admin, Order, Food, and Customer classes are connected via Composition, Aggregation, Dependencies, and Association.

Collections: HashMap and ArrayList are used to organize menu, orders, and customer data efficiently.

File Handling: Order history and temporary cart data are saved using files, ensuring persistence across sessions.

I/O Stream Management: Ensures updates done in CLI are reflected in GUI (GUI is read-only).

🎯 Features
CLI Features

Students can browse the canteen menu, place orders, and track deliveries.

Canteen staff can manage menu items and process orders.

Maintains order histories (for every customer).

Temporary cart storage via files, updated in real-time.

GUI Features (JavaFX)

Enhanced GUI for easy browsing:

Page 1 → Menu (items with name, price, availability).

Page 2 → Pending Orders (order number, items, status).

GUI is display-only (no updates allowed). Updates are made via CLI.

CLI and GUI exchange data using I/O Stream Management.

Order and cart data saved with File Handling.

🧪 JUnit Testing Implemented

Ordering Out-of-Stock Items

Tests system’s ability to block unavailable items.

Verifies error message and prevents processing.

Cart Operations

Adding an item → ensures total price updates correctly.

Modifying quantity → recalculates total price accurately.

Negative quantity attempt → blocked with proper validation.

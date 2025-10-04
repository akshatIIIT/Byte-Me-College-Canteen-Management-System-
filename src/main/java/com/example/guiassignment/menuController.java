package com.example.guiassignment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.*;
import java.util.ArrayList;

public class menuController {

    @FXML
    private TableView<Food> menuTable;

    @FXML
    private TableColumn<Food, String> itemNameColumn;

    @FXML
    private TableColumn<Food, Double> priceColumn;

    @FXML
    private TableColumn<Food, String> categoryColumn;

    @FXML
    private TableColumn<Food, String> availabilityColumn;

    private ObservableList<Food> menuItems;

    @FXML
    public void initialize() {
        // Set up table columns to map to Food object properties
        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        availabilityColumn.setCellValueFactory(new PropertyValueFactory<>("availability"));

        // Load menu items from file
        menuItems = FXCollections.observableArrayList(loadItemsFromFile());

        // Set items to the table view
        menuTable.setItems(menuItems);
    }

    // Method to load items from the saved file
    @SuppressWarnings("unchecked")
    private ArrayList<Food> loadItemsFromFile() {
        ArrayList<Food> items = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("globalItems.dat"))) {
            items = (ArrayList<Food>) ois.readObject();
            System.out.println("Items loaded successfully from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading items: " + e.getMessage());
        }
        return items;
    }
}

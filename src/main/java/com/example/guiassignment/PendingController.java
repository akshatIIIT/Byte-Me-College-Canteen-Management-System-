package com.example.guiassignment;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class PendingController {

    @FXML
    private TableView<Order> ordersTable;

    @FXML
    private TableColumn<Order, Integer> orderIdColumn;

    @FXML
    private TableColumn<Order, Integer> customerIdColumn;

    @FXML
    private TableColumn<Order, String> orderedItemsColumn;

    @FXML
    private TableColumn<Order, String> orderStatusColumn;

    @FXML
    private TableColumn<Order, Double> orderTotalColumn;

    private Admin admin;
    @FXML
    public void initialize() {

        admin = new Admin("Admin");
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        customerIdColumn.setCellValueFactory(data ->
                new SimpleObjectProperty<>(data.getValue().getCustomer().getId())
        );
        orderedItemsColumn.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getOrderedItemsAsString())
        );
        orderStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        orderTotalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        loadPendingOrders();
    }
    private void loadPendingOrders() {
        ArrayList<Order> pendingOrders = admin.viewPendingOrders();
        ObservableList<Order> observableOrders = FXCollections.observableArrayList(pendingOrders);
        ordersTable.setItems(observableOrders);
    }
}

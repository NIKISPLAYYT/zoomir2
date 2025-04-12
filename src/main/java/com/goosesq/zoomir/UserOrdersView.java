package com.goosesq.zoomir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class UserOrdersView {
    @FXML private TableView<Order> ordersTable;
    @FXML private TableColumn<Order, Integer> idColumn;
    @FXML private TableColumn<Order, String> dateColumn;
    @FXML private TableColumn<Order, String> nameColumn;
    @FXML private TableColumn<Order, Integer> quantityColumn;

    // Хранение данных
    private ObservableList<Order> orderData = FXCollections.observableArrayList();

    // Инициализация
    public void initialize() {
        // Привязка колонок к свойствам модели
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // Загрузка данных при старте
        loadOrders();
    }

    // Загрузка данных из БД
    private void loadOrders() {
        try {
            orderData.clear();
            orderData.addAll(DatabaseHandler.getAllOrders());
            ordersTable.setItems(orderData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Возврат в главное меню пользователя
    @FXML
    private void goBack() {
        MainApp.showUserMenuScreen();
    }
}
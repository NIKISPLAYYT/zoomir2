package com.goosesq.zoomir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class UserProductsView {
    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, Integer> idColumn;
    @FXML private TableColumn<Product, String> nameColumn;
    @FXML private TableColumn<Product, Integer> quantityColumn;

    // Хранение данных
    private ObservableList<Product> productData = FXCollections.observableArrayList();

    // Инициализация
    public void initialize() {
        // Привязка колонок к свойствам модели
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // Загрузка данных при старте
        loadProducts();
    }

    // Загрузка данных из БД
    private void loadProducts() {
        try {
            productData.clear();
            productData.addAll(DatabaseHandler.getAllProducts());
            productsTable.setItems(productData);
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
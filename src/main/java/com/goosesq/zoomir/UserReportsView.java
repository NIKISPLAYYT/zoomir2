package com.goosesq.zoomir;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class UserReportsView {
    @FXML private TableView<Sale> reportsTable;
    @FXML private TableColumn<Sale, Integer> idColumn;
    @FXML private TableColumn<Sale, String> productColumn;
    @FXML private TableColumn<Sale, Integer> quantityColumn;
    @FXML private TableColumn<Sale, Double> priceColumn;
    @FXML private TableColumn<Sale, Double> totalColumn;

    // Хранение данных
    private ObservableList<Sale> reportData = FXCollections.observableArrayList();

    // Инициализация
    public void initialize() {
        // Привязка колонок к свойствам модели
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));

        // Загрузка данных при старте
        loadReports();
    }

    // Загрузка данных из БД
    private void loadReports() {
        try {
            reportData.clear();
            reportData.addAll(DatabaseHandler.getAllSales());
            reportsTable.setItems(reportData);
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
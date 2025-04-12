package com.goosesq.zoomir;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class AdminMenu {
    // Блоки меню
    @FXML private AnchorPane pane_orders;
    @FXML private AnchorPane pane_products;
    @FXML private AnchorPane pane_reports;
    @FXML private AnchorPane pane_about_dev;
    @FXML private AnchorPane pane_about_prog;
    @FXML private AnchorPane pane_exit_app;
    @FXML private AnchorPane pane_logout;

    // Обработка кликов по разделам
    @FXML
    private void handleOrders(MouseEvent event) {
        MainApp.switchScreen("admin/orders.fxml");
    }

    @FXML
    private void handleProducts(MouseEvent event) {
        MainApp.switchScreen("admin/products.fxml");
    }

    @FXML
    private void handleReports(MouseEvent event) {
        MainApp.switchScreen("admin/reports.fxml");
    }

    // Информация о разработчике
    @FXML
    private void showAboutDev(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О разработчике");
        alert.setContentText("Разработчик: Ваше имя\nДата: 2023 г.");
        alert.showAndWait();
    }

    // Информация о программе
    @FXML
    private void showAboutProg(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("О программе");
        alert.setContentText("Zoomir Admin v1.0\nСистема управления зоомагазином");
        alert.showAndWait();
    }

    // Выход из приложения
    @FXML
    private void exitApp(MouseEvent event) {
        System.exit(0);
    }

    // Выход из учетной записи
    @FXML
    private void logout(MouseEvent event) {
        MainApp.showAuthScreen();
    }
}
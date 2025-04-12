package com.goosesq.zoomir;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UserMenu {
    // Связываем элементы интерфейса
    @FXML private AnchorPane pane_orders;
    @FXML private AnchorPane pane_products;
    @FXML private AnchorPane pane_reports;
    @FXML private AnchorPane pane_about_dev;
    @FXML private AnchorPane pane_about_prog;
    @FXML private AnchorPane pane_exit_app;
    @FXML private AnchorPane pane_logout;

    // Обработка клика на "Заказы"
    @FXML
    private void handleOrders(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("orders_view.fxml"));
            Scene newScene = new Scene(loader.load());
            MainApp.getPrimaryStage().setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Обработка клика на "Товары"
    @FXML
    private void handleProducts(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("products_view.fxml"));
            Scene newScene = new Scene(loader.load());
            MainApp.getPrimaryStage().setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Обработка клика на "Отчеты"
    @FXML
    private void handleReports(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("reports_view.fxml"));
            Scene newScene = new Scene(loader.load());
            MainApp.getPrimaryStage().setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        alert.setContentText("Zoomir v1.0\nСистема управления зоомагазином");
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("auth.fxml"));
            Scene newScene = new Scene(loader.load());
            MainApp.getPrimaryStage().setScene(newScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.goosesq.zoomir;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {
    // Сделали поле приватным
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        showAuthScreen();
    }

    // Добавили геттер
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    // Метод показа авторизации
    public static void showAuthScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("auth.fxml"));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод показа регистрации
    public static void showRegistrationScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("reg.fxml"));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод показа пользовательского меню
    public static void showUserMenuScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("user_menu.fxml"));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void switchScreen(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlPath));
            getPrimaryStage().setScene(new Scene(loader.load()));
            getPrimaryStage().show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для показа меню администратора
    public static void showAdminMenuScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("admin_menu.fxml"));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
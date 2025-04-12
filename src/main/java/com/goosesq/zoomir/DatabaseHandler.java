package com.goosesq.zoomir;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final String URL = "jdbc:mysql://localhost:3306/db_zoomir";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static User checkUser(String login, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM t_users WHERE login = ? AND password = ?"
            );
            statement.setString(1, login);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id_user"),
                        resultSet.getString("name"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getString("position")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Добавление нового пользователя
    public static void addUser(User user) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO t_users (name, login, password, position) VALUES (?, ?, ?, ?)"
            );
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword()); // В реальном проекте хешируйте пароль
            statement.setString(4, user.getRole());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Проверка существования логина
    public static boolean isLoginExists(String login) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT COUNT(*) FROM t_users WHERE login = ?"
            );
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Метод получения всех заказов
    public static List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM t_zakaz");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id_zakaz"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("name"),
                        rs.getInt("count")
                ));
            }
        }
        return orders;
    }

    // Добавление заказа
    public static void addOrder(Order order) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO t_zakaz (date, name, count) VALUES (?, ?, ?)"
            );
            statement.setDate(1, Date.valueOf(order.getDate()));
            statement.setString(2, order.getName());
            statement.setInt(3, order.getQuantity());
            statement.executeUpdate();
        }
    }

    // Удаление заказа
    public static void deleteOrder(int orderId) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM t_zakaz WHERE id_zakaz = ?"
            );
            statement.setInt(1, orderId);
            statement.executeUpdate();
        }
    }

    // Получение заказа по ID
    public static Order getOrderById(int orderId) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM t_zakaz WHERE id_zakaz = ?"
            );
            statement.setInt(1, orderId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Order(
                        rs.getInt("id_zakaz"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("name"),
                        rs.getInt("count")
                );
            }
        }
        return null;
    }

    // Метод получения всех продаж
    public static List<Sale> getAllSales() throws SQLException {
        List<Sale> sales = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM t_sallers");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                sales.add(new Sale(
                        rs.getInt("id_sale"),
                        rs.getString("tovar"),
                        rs.getInt("count"),
                        rs.getDouble("cena")
                ));
            }
        }
        return sales;
    }

    // Добавление продажи
    public static void addSale(Sale sale) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO t_sallers (tovar, count, cena) VALUES (?, ?, ?)"
            );
            statement.setString(1, sale.getProduct());
            statement.setInt(2, sale.getQuantity());
            statement.setDouble(3, sale.getPrice());
            statement.executeUpdate();
        }
    }

    // Удаление продажи
    public static void deleteSale(int saleId) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM t_sallers WHERE id_sale = ?"
            );
            statement.setInt(1, saleId);
            statement.executeUpdate();
        }
    }

    // Получение продажи по ID
    public static Sale getSaleById(int saleId) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM t_sallers WHERE id_sale = ?"
            );
            statement.setInt(1, saleId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Sale(
                        rs.getInt("id_sale"),
                        rs.getString("tovar"),
                        rs.getInt("count"),
                        rs.getDouble("cena")
                );
            }
        }
        return null;
    }

    // Метод получения всех товаров
    public static List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM t_tovars");
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id_tovar"),
                        rs.getString("name"),
                        rs.getInt("count")
                ));
            }
        }
        return products;
    }

    // Добавление товара
    public static void addProduct(Product product) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO t_tovars (name, count) VALUES (?, ?)"
            );
            statement.setString(1, product.getName());
            statement.setInt(2, product.getQuantity());
            statement.executeUpdate();
        }
    }

    // Удаление товара
    public static void deleteProduct(int productId) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM t_tovars WHERE id_tovar = ?"
            );
            statement.setInt(1, productId);
            statement.executeUpdate();
        }
    }

    // Получение товара по ID
    public static Product getProductById(int productId) throws SQLException {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM t_tovars WHERE id_tovar = ?"
            );
            statement.setInt(1, productId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("id_tovar"),
                        rs.getString("name"),
                        rs.getInt("count")
                );
            }
        }
        return null;
    }

}

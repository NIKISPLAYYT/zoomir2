package com.goosesq.zoomir;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Sale {
    private final IntegerProperty id;
    private final StringProperty product;
    private final IntegerProperty quantity;
    private final DoubleProperty price;
    private final DoubleProperty total;

    public Sale(int id, String product, int quantity, double price) {
        this.id = new SimpleIntegerProperty(id);
        this.product = new SimpleStringProperty(product);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
        this.total = new SimpleDoubleProperty(quantity * price);
    }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }

    public String getProduct() { return product.get(); }
    public StringProperty productProperty() { return product; }

    public int getQuantity() { return quantity.get(); }
    public IntegerProperty quantityProperty() { return quantity; }

    public double getPrice() { return price.get(); }
    public DoubleProperty priceProperty() { return price; }

    public double getTotal() { return total.get(); }
    public DoubleProperty totalProperty() { return total; }
}
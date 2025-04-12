package com.goosesq.zoomir;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Order {
    private final IntegerProperty id;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty name;
    private final IntegerProperty quantity;

    public Order(int id, LocalDate date, String name, int quantity) {
        this.id = new SimpleIntegerProperty(id);
        this.date = new SimpleObjectProperty<>(date);
        this.name = new SimpleStringProperty(name);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    public int getId() { return id.get(); }
    public IntegerProperty idProperty() { return id; }

    public LocalDate getDate() { return date.get(); }
    public ObjectProperty<LocalDate> dateProperty() { return date; }

    public String getName() { return name.get(); }
    public StringProperty nameProperty() { return name; }

    public int getQuantity() { return quantity.get(); }
    public IntegerProperty quantityProperty() { return quantity; }
}
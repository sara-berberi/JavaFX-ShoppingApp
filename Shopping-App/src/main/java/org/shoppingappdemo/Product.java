package org.shoppingappdemo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Product {
    private final int id;
    private final String productName;
    private final String shortDescription;
    private final double price;
    private final int quantity;
    private final BooleanProperty selected;
    private final IntegerProperty quantityProperty = new SimpleIntegerProperty(1);

    public Product(int id, String productName, String shortDescription, double price, int quantity) {
        this.id = id;
        this.productName = productName;
        this.shortDescription = shortDescription;
        this.price = price;
        this.quantity = quantity;
        this.selected = new SimpleBooleanProperty(false);
    }

    public int getQuantityP() {
        return quantityProperty().get();
    }

    public IntegerProperty quantityProperty() {
        return quantityProperty;
    }

    public void SetQuantityP(int quantity) {
        quantityProperty().set(quantity);
    }


    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    @Override
    public String toString() {
        return productName;
    }
}

package com.example.gui_final_project;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class InventoryItem {
    private final SimpleStringProperty itemName;
    private final SimpleIntegerProperty quantity;
    private final SimpleDoubleProperty price;

    public InventoryItem (String itemName, int quantity, double price){
        this.itemName = new SimpleStringProperty(itemName);
        this.quantity = new SimpleIntegerProperty(quantity);
        this.price = new SimpleDoubleProperty(price);
    }

    public String getItemName(){
        return itemName.get();
    }

    public void setItemName(String itemName){
        this.itemName.set(itemName);
    }

    public SimpleStringProperty itemNameProperty(){
        return itemName;
    }

    public int getQuantity(){
        return quantity.get();
    }

    public void setQuantity(int quantity){
        this.quantity.set(quantity);
    }

    public SimpleIntegerProperty quantityProperty(){
        return quantity;
    }

    public double getPrice(){
        return price.get();
    }
    public void setPrice(double price){
        this.price.set(price);
    }
    public SimpleDoubleProperty priceProperty(){
        return price;
    }
}

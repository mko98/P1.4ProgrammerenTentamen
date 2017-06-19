package com.example.max.programmerententamen.domain;

import java.io.Serializable;

public class Film implements Serializable {

    private String name;
    private int customerID;
    private int inventoryID;
    private int filmID;


    public Film(String name, int customerID, int inventoryID, int filmID) {
        this.name = name;
        this.customerID = customerID;
        this.inventoryID = inventoryID;
        this.filmID = filmID;
    }

    public Film(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(int inventoryID) {
        this.inventoryID = inventoryID;
    }

    public int getFilmID() {
        return filmID;
    }

    public void setFilmID(int filmID) {
        this.filmID = filmID;
    }
}

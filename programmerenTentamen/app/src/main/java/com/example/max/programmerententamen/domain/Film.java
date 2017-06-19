package com.example.max.programmerententamen.domain;

import java.io.Serializable;

public class Film implements Serializable {

    private String name;


    public Film(String name){

        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

package com.example.max.programmerententamen.domain;

import java.io.Serializable;

/**
 * Created by Maikel on 1-6-2017.
 */

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

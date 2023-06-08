package com.example.testlistview;


import java.io.Serializable;

public class Patient implements Serializable {
    private String name;
    private String amka;
    private String street;

    private int image;


    public Patient(String name, String amka, String street, int image){
        this.name = name;
        this.amka = amka;
        this.street = street;
        this.image = image;
    }

    public String getName(){
        return name;
    }
    public String getAmka(){
        return amka;
    }
    public String getStreet(){
        return street;
    }

    public int getImage(){
        return image;
    }



}

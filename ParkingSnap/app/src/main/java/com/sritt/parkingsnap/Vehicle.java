package com.sritt.parkingsnap;

import java.io.Serializable;

public class Vehicle implements Serializable {
    String firstName;
    String lastName;
    String licence;
    String year;
    String make;
    String model;
    String color;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Vehicle(String firstName, String lastName, String licence, String year, String make, String model, String color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.licence = licence;
        this.year = year;
        this.make = make;
        this.model = model;
        this.color = color;
    }

    public String toString(){
        return("Name: "+ firstName+" "+lastName+"\nLicense Plate Number: "+licence+"\nVehicle: "+make+" "+model+" "+year+"\nColor: "+color);
    }
}

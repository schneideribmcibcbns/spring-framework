package com.logicbig.example;

import com.opencsv.bean.CsvBindByName;

public class Employee {
    
    @CsvBindByName
    private String id;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String phoneNumber;
    
    public Employee () {
    }
    
    public Employee (String id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    
    public String getId () {
        return id;
    }
    
    public void setId (String id) {
        this.id = id;
    }
    
    public String getName () {
        return name;
    }
    
    public void setName (String name) {
        this.name = name;
    }
    
    public String getPhoneNumber () {
        return phoneNumber;
    }
    
    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    @Override
    public String toString () {
        return "Employee{" +
                  "id='" + id + '\'' +
                  ", name='" + name + '\'' +
                  ", phoneNumber='" + phoneNumber + '\'' +
                  '}';
    }
}
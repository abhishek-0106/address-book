package com.example.addressbook.model;

import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class Contact {

    private String id;
    private String name;
    private String phone;
    private String email;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String generateId(){
        return UUID.randomUUID().toString();
    }


}

package com.example.arielcast;

public class User {
    public String full_name, phone, email;

    public User(){

    }


    public User(String full_name, String phone, String email) {
        this.full_name = full_name;
        this.phone = phone;
        this.email = email;
    }

    public User(String full_name, String email) {
        this.full_name = full_name;
        this.email = email;
    }
}

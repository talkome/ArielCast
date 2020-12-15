package com.example.arielcast.firebase.model.dataObject;

import android.content.Context;

public class LecturerObj {
    String lecturerId;
     String email;
     String password;
     String fullname;
     String phone;



    public LecturerObj(String lecturerId,String email,String password,String fullname,String phone)
    {
        this.lecturerId=lecturerId;
        this.email=email;
        this.password=password;
        this.fullname=fullname;
        this.phone=phone;

    }

    public LecturerObj() {

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getLecturerId() {
        return lecturerId;
    }
}

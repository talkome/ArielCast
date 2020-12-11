package com.example.arielcast.firebase.model.dataObject;

import android.content.Context;

public class LecturerObj {
     String email;
     String password;
     String fullname;
     String phone;
     String courseName;


    public LecturerObj(String email,String password,String fullname,String phone,String courseName)
    {
        this.email=email;
        this.password=password;
        this.fullname=fullname;
        this.phone=phone;
        this.courseName=courseName;
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

    public void setFullname(String fname) {
        this.fullname = fname;
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

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

}

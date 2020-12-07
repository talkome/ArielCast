package com.example.arielcast.firebase.model.dataObject;

public class StudentObj {
     String email;
     String fullname;
     String phone;
     String password;

    public StudentObj(String email,String fullname,String phone,String password)
    {
        this.email=email;
        this.fullname=fullname;
        this.phone=phone;
        this.password=password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setFname(String fname) {
        this.fullname = fname;
    }

    public String getFname() {
        return fullname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
}

package com.example.arielcast.firebase.model.dataObject;

public class LecturerObj {
     String email;
     String password;
     String fname;
     String lname;
     String phone;
     String faculty;

    public LecturerObj(String email,String password,String fname,String lname,String phone,String faculty)
    {
        this.email=email;
        this.password=password;
        this.fname=fname;
        this.lname=lname;
        this.phone=phone;
        this.faculty=faculty;
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
        this.fname = fname;
    }

    public String getFname() {
        return fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getLname() {
        return lname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getFaculty() {
        return faculty;
    }

}

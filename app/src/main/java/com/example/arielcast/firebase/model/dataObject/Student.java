package com.example.arielcast.firebase.model.dataObject;

public class Student {
    String studentId;
     String email;
     String fullname;
     String phone;
     String password;

    public Student(String id, String email, String fullname, String phone, String password)
    {
        this.studentId=id;
        this.email=email;
        this.fullname=fullname;
        this.phone=phone;
        this.password=password;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullname(String fullname) { this.fullname = fullname;}

    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPassword() {
        return password;
    }
}

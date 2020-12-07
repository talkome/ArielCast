package com.example.arielcast.firebase.model;

import com.example.arielcast.firebase.model.dataObject.LecturerObj;
import com.google.firebase.database.DatabaseReference;

public class FirebaseDBLecturers extends FirebaseBaseModel {
    public void addLecturerToDB(String email,String password,String fullname,String phone,String faculty)
    {
        LecturerObj lecturerReg=new LecturerObj(email,password,fullname,phone,faculty);
        myRef.child("lecturers").child(email).setValue(lecturerReg);
    }

    public DatabaseReference getLecturerFromDB(String lecturerID)
    {
        return myRef.child("lecturers").child(lecturerID);
    }

}

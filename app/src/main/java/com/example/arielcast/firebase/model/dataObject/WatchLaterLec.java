package com.example.arielcast.firebase.model.dataObject;

public class WatchLaterLec {
    String studentID , lectureID;

    public WatchLaterLec(String studentID, String lectureID) {
        this.studentID = studentID;
        this.lectureID = lectureID;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getLectureID() {
        return lectureID;
    }

    public void setLectureID(String lectureID) {
        this.lectureID = lectureID;
    }
}

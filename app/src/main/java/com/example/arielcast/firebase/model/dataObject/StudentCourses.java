package com.example.arielcast.firebase.model.dataObject;

public class StudentCourses {

    String studentId , courseId;

    public StudentCourses(String studentId,String courseId) {
        this.courseId=courseId;
        this.studentId=studentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseId() {
        return courseId;
    }


    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}

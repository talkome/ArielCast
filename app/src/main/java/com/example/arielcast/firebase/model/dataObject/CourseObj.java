package com.example.arielcast.firebase.model.dataObject;

public class CourseObj {
    String courseId;
    String courseName;
    String lecturerId;
    String semester;
    int year;
    int credits;

    public  CourseObj(String courseId,String courseName,String lecturerId,String semester,int year,int credits)
    {
        this.courseId=courseId;
        this.courseName=courseName;
        this.lecturerId=lecturerId;
        this.semester=semester;
        this.year=year;
        this.credits=credits;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setLecturerId(String lecturerId) {
        this.lecturerId = lecturerId;
    }

    public String getLecturerId() {
        return lecturerId;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSemester() {
        return semester;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getCredits() {
        return credits;
    }
}

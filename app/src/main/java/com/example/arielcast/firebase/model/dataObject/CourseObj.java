package com.example.arielcast.firebase.model.dataObject;

public class CourseObj {
    String courseId;
    String courseName;
    String lecturerId;
    String semester;
    String year;
    String credits;

    public  CourseObj(String courseName, String lecturerId, String semester, String year, String credits, String courseId)
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

    public void setYear(String year) {
        this.year = year;
    }

    public String getYear() {
        return year;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getCredits() {
        return credits;
    }
}

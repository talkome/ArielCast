package com.example.arielcast.firebase.model.dataObject;

import android.widget.ImageView;

public class Course {
    int image;
    Integer courseId;
    String courseName;
    String lecturerId;
    String semester;
    String year;
    String description;

    public Course() {
        this.courseId = 0;
        this.courseName = "";
        this.lecturerId = "";
        this.semester = "";
        this.year = "";
        this.description = "";
        this.image = 0;
    }

    public Course(Integer courseId, String courseName, String lecturerId, String semester, String year, String credits, String description, int image) {
        this.courseId=courseId;
        this.courseName=courseName;
        this.lecturerId=lecturerId;
        this.semester=semester;
        this.year=year;
        this.description = description;
        this.image = image;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public Integer getCourseId() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

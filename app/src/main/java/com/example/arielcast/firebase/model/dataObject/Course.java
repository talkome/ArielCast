package com.example.arielcast.firebase.model.dataObject;

import android.widget.ImageView;

import java.text.SimpleDateFormat;

public class Course
{
    int image;
    Integer courseId;
    String courseName;
    String lecturerId;
    String startDate;
    String endDate;


    public Course() {
        this.courseId = 0;
        this.courseName = "";
        this.lecturerId = "";
        this.startDate = "";
        this.endDate = "";
        this.image = 0;
    }

    public Course(Integer courseId, String courseName, String lecturerId, String start, String end, String description, int image) {
        this.courseId=courseId;
        this.courseName=courseName;
        this.lecturerId=lecturerId;
        this.startDate=start;
        this.endDate=end;
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

    public void setSemester(String startDate) {
        this.startDate = startDate;
    }

    public String getSemester() {
        return startDate;
    }

    public void setYear(String endDate) {
        this.endDate = endDate;
    }

    public String getYear() {
        return endDate;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

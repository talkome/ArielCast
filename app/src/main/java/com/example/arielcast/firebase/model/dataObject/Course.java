package com.example.arielcast.firebase.model.dataObject;

import android.net.Uri;
import android.widget.ImageView;

import java.text.SimpleDateFormat;

public class Course
{
    String image;
    String courseId;
    String courseName;
    String lecturerId;
    String startDate;
    String endDate;



    public Course() {
        this.courseId = "";
        this.courseName = "";
        this.lecturerId = "";
        this.startDate = "";
        this.endDate = "";
        this.image = "";

    }

    public Course(String courseId, String courseName, String lecturerId, String start, String end, String image) {
        this.courseId=courseId;
        this.courseName=courseName;
        this.lecturerId=lecturerId;
        this.startDate=start;
        this.endDate=end;
        this.image = image;
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

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}

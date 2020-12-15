package com.example.arielcast.firebase.model;

import com.example.arielcast.firebase.model.dataObject.CourseObj;
import com.google.firebase.database.DatabaseReference;

public class FirebaseDBCourses extends FirebaseBaseModel
{

    public void addCourseToDB(String courseId,String courseName,String lecturerId,String semester,int year,int credits)
    {
       // CourseObj courseReg=new CourseObj(courseName, lecturerId, semester, year, credits, courseId);
      //  myRef.child("courses").child(courseId).setValue(courseReg);
    }

    public DatabaseReference getCourseFromDB(String courseID)
    {
        return myRef.child("courses").child(courseID);
    }

    public void WriteSearchCourse(String courseId,String courseName,String lecturerId,String semester,int year,int credits)
    {
      //  CourseObj course = new CourseObj(courseName, lecturerId, semester, year, credits, courseId);
      //  course.setCourseId(courseId);
       // myRef.child("searchCourse").child(course.getCourseName()).child(courseId).setValue(course);
    }


}

package com.example.arielcast;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arielcast.firebase.model.dataObject.Course;

import java.util.ArrayList;

public class MyLecturesAdapter extends RecyclerView.Adapter<MyHolder> {
        Context context;
        ArrayList<Course> courses;

public MyLecturesAdapter(Context context, ArrayList<Course> courses) {
        this.context = context;
        this.courses = courses;
        }

@NonNull
@Override
public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_course,null);
        return new MyHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.mTitle.setText(courses.get(position).getCourseName());
        holder.mDes.setText(courses.get(position).getDescription());
        //   holder.mImageView.setImageResource(courses.get(position).getImage());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent intent=new Intent(context,ShowCourse.class);
        intent.putExtra("CourseId",courses.get(position).getCourseId());
        context.startActivity(intent);
        }
        });
        }

@Override
public int getItemCount() {
        return courses.size();
        }
        }

package com.example.arielcast;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arielcast.firebase.model.dataObject.Course;
import com.example.arielcast.firebase.model.dataObject.Lecture;

import java.util.ArrayList;

public class MyLecturesAdapter extends RecyclerView.Adapter<MyHolder> {
        Context context;
        ArrayList<Lecture> lectures;
        String userID;

public MyLecturesAdapter(Context context, ArrayList<Lecture> lectures, String userId) {
        this.context = context;
        this.lectures = lectures;
        this.userID=userId;
        }

@NonNull
@Override
public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_lecture,null);
        return new MyHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.mTitle.setText(lectures.get(position).getLectureName());
        holder.mDes.setText(lectures.get(position).getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        Intent intent=new Intent(context,ShowLecture.class);
        intent.putExtra("CourseId",lectures.get(position).getCourseId());
        intent.putExtra("lecID",lectures.get(position).getLectureName());
        intent.putExtra("lecturerId",lectures.get(position).getLecturerId());
        intent.putExtra("ID",userID);
        context.startActivity(intent);
        }
        });
        }

@Override
public int getItemCount() {
        return lectures.size();
        }
        }

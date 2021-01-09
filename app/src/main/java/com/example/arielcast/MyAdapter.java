package com.example.arielcast;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arielcast.firebase.model.dataObject.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {
    Context context;
    ArrayList<Course> courses;
    String userId;

    public MyAdapter(Context context, ArrayList<Course> courses, String userID) {
        this.context = context;
        this.courses = courses;
        this.userId=userID;
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


        // get lecturer name
        String lecId=courses.get(position).getLecturerId();

        DatabaseReference myRef= FirebaseDatabase.getInstance().getReference().child("Lecturers").child(lecId);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String lecName=snapshot.child("fullname").getValue(String.class);

                holder.mDes.setText(lecName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //TODO : get uri from image link (firebase storage)
      //  holder.mImageView.setImageURI(courses.get(position).getImage_uri());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ShowCourse.class);
                intent.putExtra("CourseId",courses.get(position).getCourseId());
                intent.putExtra("lecID",courses.get(position).getLecturerId());
                intent.putExtra("ID",userId);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }
}

package com.example.arielcast;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arielcast.firebase.model.dataObject.Course;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static android.widget.Toast.makeText;

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

        holder.mImageView.findViewById(R.id.lecture_image);

       //  get uri from image link
        Picasso.with(context).load(courses.get(position).getImage()).fit().into((ImageView)holder.mImageView);

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

    public void filterList(ArrayList<Course> filteredList){
        courses = filteredList;
        notifyDataSetChanged();
    }
}

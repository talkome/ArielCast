package com.example.arielcast;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arielcast.firebase.model.dataObject.Course;
import com.example.arielcast.firebase.model.dataObject.StudentCourses;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

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


        //check if it's lecturer user

        Query query = FirebaseDatabase.getInstance().getReference().child("Lecturers").orderByChild("lecturerId").equalTo(userId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                        // if it's lecturer user - > dont show plus button
                        // and send email for all student tha follow this course
                    Drawable d = ContextCompat.getDrawable(context, R.drawable.peoplesnip);
                       holder.plusImage.setImageDrawable(d);
                       //open popUp window that show student that follow this course
                       holder.plusImage.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               // show students list - followers in this course

//                               Intent intent=new Intent(context, popActivity.class);
//                               intent.putExtra("CourseId",courses.get(position).getCourseId());
//                               intent.putExtra("lecID",courses.get(position).getLecturerId());
//                               intent.putExtra("ID",userId);
//                               context.startActivity(intent);
                           }
                       });

                       //send email to student that follow this course
                       holder.emailImage.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               Intent intent=new Intent(context, MailActivity.class);
                               intent.putExtra("CourseId",courses.get(position).getCourseId());
                               intent.putExtra("lecID",courses.get(position).getLecturerId());
                               intent.putExtra("ID",userId);
                               context.startActivity(intent);
                           }
                       });

                } else {
                    Query query = FirebaseDatabase.getInstance().getReference().child("Students").child(userId);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot data : snapshot.getChildren()) {
                                    // if it's student user it's possible to follow this course
                                    // and student also can send email to lecturer's course .
                                    holder.plusImage.setVisibility(View.VISIBLE);

                                    // follow course
                                 //   holder.plusImage.setOnClickListener(new View.OnClickListener() {
                                    //    @Override
                                     //   public void onClick(View v) {
                                            String SCId = userId+"-"+courses.get(position).getCourseId();
                                            StudentCourses user = new StudentCourses(userId, courses.get(position).getCourseId());
                                             DatabaseReference ref=FirebaseDatabase.getInstance().getReference("StudentCourses");

                                             ref.addValueEventListener(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                     if (snapshot.child(SCId).exists()) {
                                                         Drawable d = ContextCompat.getDrawable(context, R.drawable.tempsnip11);
                                                         holder.plusImage.setImageDrawable(d);
                                                         holder.plusImage.setOnClickListener(new View.OnClickListener() {
                                                             @Override
                                                             public void onClick(View v) {
                                                                 Toast.makeText(context,
                                                                         "this course removed from your list !",
                                                                         Toast.LENGTH_LONG).show();
                                                         ref.child(SCId).removeValue();
                                                                 Drawable d = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_add);
                                                                 holder.plusImage.setImageDrawable(d);
                                                     }
                                                 });
                                                     } else {
                                                          holder.plusImage.setOnClickListener(new View.OnClickListener() {
                                                         @Override
                                                                   public void onClick(View v) {
                                                                         ref.child(SCId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                             @Override
                                                             public void onComplete(@NonNull Task<Void> task) {
                                                                 if (task.isSuccessful()) {
                                                                     Toast.makeText(context,
                                                                             "this course added in your list !",
                                                                             Toast.LENGTH_LONG).show();

                                                                     Drawable d = ContextCompat.getDrawable(context, R.drawable.tempsnip11);
                                                                     holder.plusImage.setImageDrawable(d);
                                                                 }
                                                             }
                                                         });
                                                     }
                                                 });
                                                     }
                                                 }

                                                 @Override
                                                 public void onCancelled(@NonNull DatabaseError error) {

                                                 }
                                             });


                                }
                                }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException(); // don't ignore errors
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

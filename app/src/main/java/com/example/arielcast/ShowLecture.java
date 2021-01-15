package com.example.arielcast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ShowLecture extends AppCompatActivity {
    String lectureID;
    String videoPath;
    EditText lecture_name;
    ImageButton editButton,deleteButton , addToPlaylist;
    TextView lecNameText , dateText;
    DatabaseReference dataRef;
    String lecturername;
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_lecture);

        lecture_name = findViewById(R.id.et_video_name);
        lecNameText=findViewById(R.id.textViewSub_lecName);
        dateText=findViewById(R.id.textViewSub_date);
        editButton=findViewById(R.id.editButton5);
        deleteButton=findViewById(R.id.deleteButton);
        addToPlaylist=findViewById(R.id.imageButton4);

        lectureID = getIntent().getExtras().getString("lecID");
        String id =getIntent().getExtras().getString("ID");
        String lecturerId= getIntent().getExtras().getString("lecturerId");
        lecture_name.setText(lectureID);


        //check if it's lecturer user
        Query query = FirebaseDatabase.getInstance().getReference().child("Lecturers").orderByChild("lecturerId").equalTo(id);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for(DataSnapshot data:snapshot.getChildren()) {

                        // if lecturer
                        editButton.setVisibility(View.VISIBLE);
                        deleteButton.setVisibility(View.VISIBLE);
                        addToPlaylist.setVisibility(View.INVISIBLE);

                        //delete lecture :

                        deleteButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myDialog =new Dialog(ShowLecture.this);
                                myDialog.setContentView(R.layout.delete_course_dialog);
                                myDialog.setTitle("Delete this lecture ?");
                                TextView hello=(TextView) myDialog.findViewById(R.id.hello);
                                hello.setText("Are you sure you want to delete this lecture ?");
                                Button db=(Button)myDialog.findViewById(R.id.db) ;
                                Button cb=(Button)myDialog.findViewById(R.id.cb) ;
                                db.setText("Delete this lecture");
                                ImageView iv=(ImageView)myDialog.findViewById(R.id.imv) ;
                                myDialog.show();
                                cb.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        myDialog.cancel();
                                    }
                                });
                                db.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DatabaseReference refe=FirebaseDatabase.getInstance().getReference().child("Lectures");
                                        refe.child(lectureID).removeValue();
                                        Intent i=new Intent(ShowLecture.this, MainActivity.class);
                                        i.putExtra("ID",id);
                                        startActivity(i);
                                    }
                                });
                            }
                        });
                    }
                } else {
                    editButton.setVisibility(View.INVISIBLE);
                    deleteButton.setVisibility(View.INVISIBLE);
                    addToPlaylist.setVisibility(View.VISIBLE);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException(); // don't ignore errors
            }
        });


        dataRef = FirebaseDatabase.getInstance().getReference().child("Lectures").child(lectureID);

        VideoView videoView = findViewById(R.id.lecture_view);
        dataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    videoPath = snapshot.child("video_url").getValue(String.class);
                    Uri uri = Uri.parse(videoPath);
                    videoView.setVideoURI(uri);
                    videoView.requestFocus();
                    videoView.start();


                    String date=snapshot.child("date").getValue(String.class);
                    dateText.setText(snapshot.child("date").getValue(String.class));

                    Query qe=FirebaseDatabase.getInstance().getReference().child("Lecturers").orderByChild("lecturerId").equalTo(lecturerId);

                    qe.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            lecturername=snapshot.child(lecturerId).child("fullname").getValue(String.class);
                            lecNameText.setText(lecturername);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

    }
}
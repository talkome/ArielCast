package com.example.arielcast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.arielcast.firebase.model.dataObject.StudentCourses;
import com.example.arielcast.firebase.model.dataObject.WatchLaterLec;
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
    FirebaseDatabase database;
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
        addToPlaylist=findViewById(R.id.add_to_watch_later_list);

        lectureID = getIntent().getExtras().getString("lecID");
        String id =getIntent().getExtras().getString("ID");
        String lecturerId= getIntent().getExtras().getString("lecturerId");
        lecture_name.setText(lectureID);
        String SCId = id + "-" + lectureID;
        DatabaseReference dbl = FirebaseDatabase.getInstance().getReference().child("WatchLaterLec").child(SCId);
        dbl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Drawable d = ContextCompat.getDrawable(ShowLecture.this, R.drawable.ic_baseline_playlist_add_check_24);
                    addToPlaylist.setImageDrawable(d);
                } else {
                    Drawable d = ContextCompat.getDrawable(ShowLecture.this, R.drawable.capture1212);
                    addToPlaylist.setImageDrawable(d);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        addToPlaylist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dataRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.child(SCId).exists()){
                            dataRef = FirebaseDatabase.getInstance().getReference("WatchLaterLec");
                            WatchLaterLec lec = new WatchLaterLec(id, lectureID);
                            dataRef.child(SCId).setValue(lec);
                            Toast.makeText(ShowLecture.this,"added to Watch Later",
                                    Toast.LENGTH_LONG).show();
                        } else {

                            dataRef.child(SCId).removeValue();
                            Toast.makeText(ShowLecture.this,"removed from Watch Later",
                                    Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

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
                        addToPlaylist.setVisibility(View.VISIBLE);

                        //delete lecture
                        deleteButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myDialog =new Dialog(ShowLecture.this);
                                myDialog.setContentView(R.layout.delete_course_dialog);
                                myDialog.setTitle("Delete this lecture ?");
                                TextView hello=(TextView) myDialog.findViewById(R.id.hello);
                                hello.setText("Are you sure you want to delete this lecture ?");
                                Button db=(Button)myDialog.findViewById(R.id.editb) ;
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
                if(snapshot.exists()) {
                    videoPath = snapshot.child("video_url").getValue(String.class);
                    Uri uri = Uri.parse(videoPath);
                    videoView.setVideoURI(uri);
                    videoView.requestFocus();
                    videoView.start();

                    String date = snapshot.child("date").getValue(String.class);
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
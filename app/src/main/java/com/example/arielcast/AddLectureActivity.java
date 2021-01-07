package com.example.arielcast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.arielcast.firebase.model.dataObject.Lecture;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static java.lang.System.currentTimeMillis;

public class AddLectureActivity extends AppCompatActivity{
    private static final int PICK_VIDEO = 1;
    VideoView videoView;
    Button addLec;
    ProgressBar progressBar;
    EditText editText;
    Uri videoUri;
    MediaController mediaController;
    Lecture lecture;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    UploadTask uploadTask;
    String lecturerEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_lecture);

        lecture = new Lecture();
        storageReference = FirebaseStorage.getInstance().getReference().child("Video");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("video");

        Intent intent=getIntent();
        lecturerEmail=intent.getExtras().getString("Email");

        videoView = findViewById(R.id.videoview_main);
        addLec = findViewById(R.id.addLectureButton);
        progressBar = findViewById(R.id.progressBar);
        editText = findViewById(R.id.et_video_name);
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.start();

        addLec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    UploadVideo();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_VIDEO || requestCode == RESULT_OK ||
                data != null || data.getData() != null) {
            videoUri = data.getData();
            videoView.setVideoURI(videoUri);
        }
    }

    public void ChooseVideo(View view) {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_VIDEO);
    }

    private String getExt(Uri uri){
        ContentResolver contentResolver = getContentResolver(); //
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton(); //
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)); //
    }


    public void ShowVideo(View view) {
    }

    private void UploadVideo(){
        String videoName = editText.getText().toString();
        String search = editText.getText().toString().toLowerCase();
        if (videoUri != null ) {
            if(!TextUtils.isEmpty(videoName)) {
                progressBar.setVisibility(View.VISIBLE);
                final StorageReference myRef = storageReference.child(currentTimeMillis() + "." + getExt(videoUri));
                uploadTask = myRef.putFile(videoUri);

                Task<Uri> taskurl = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return myRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();

                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(AddLectureActivity.this, "Data saved!",
                                    Toast.LENGTH_LONG).show();

                            // get Email ( from Extras) from LecturerActivity

                            lecture.setLectureName(videoName);
                            lecture.setVideo_url(downloadUri.toString());
                            lecture.setSearch(search);
                        //    lecture.setLecturerEmail(lecturerEmail); // id
                            databaseReference.child(videoName).setValue(lecture);
                            Intent i=new Intent(AddLectureActivity.this, LecturerActivity.class);
                            i.putExtra("Email",lecturerEmail);
                            startActivity(i);
                        } else {
                            Toast.makeText(AddLectureActivity.this, "Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            } else if (TextUtils.isEmpty(videoName)) {
                editText.setError("please enter video name");
                editText.requestFocus();
                return;
            } else {
            Toast.makeText(this,"All Fields are required",Toast.LENGTH_SHORT).show();
        }
    }
}

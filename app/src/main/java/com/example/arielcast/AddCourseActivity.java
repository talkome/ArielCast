package com.example.arielcast;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arielcast.firebase.model.dataObject.Course;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

import static java.lang.System.currentTimeMillis;

public class AddCourseActivity extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    EditText courseName,startDate,endDate,image;
    Button button,upload;
    ImageView imageLogo;
    ImageView imageView;
    TextView textLogo;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ProgressBar progressBar;
    Uri imageUri;
    String email,lecId;
    UploadTask uploadTask;
    Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcourse);

        courseName=findViewById(R.id.courseName);
        startDate=findViewById(R.id.startDate);
        endDate=findViewById(R.id.endDate);
        imageView=findViewById(R.id.image);
        button=findViewById(R.id.button);
        textLogo =findViewById(R.id.viewlogo);
        imageLogo =findViewById(R.id.imagelogo);
        progressBar=findViewById(R.id.progressBar2);
        upload=findViewById(R.id.uploadImage);
        course=new Course();
        storageReference = FirebaseStorage.getInstance().getReference().child("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Courses");

        // get lecturer's email from MainActivity
        Intent intent = getIntent();
         email = intent.getExtras().getString("Email");
         lecId=intent.getExtras().getString("ID");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { UploadVideo(); }
        });

    }

    private String getExt(Uri uri){
        ContentResolver contentResolver = getContentResolver(); //
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton(); //
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)); //
    }

    public void ChooseImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE || requestCode == RESULT_OK ||
                data != null || data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }


    private void UploadVideo(){
       // String videoName = editText.getText().toString();
      //  String search = editText.getText().toString().toLowerCase();
        if (imageUri != null ) {
            if(!TextUtils.isEmpty(courseName.getText())) {
                progressBar.setVisibility(View.VISIBLE);
                final StorageReference myRef = storageReference.child(currentTimeMillis() + "." + getExt(imageUri));
                uploadTask = myRef.putFile(imageUri);

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
                            Toast.makeText(AddCourseActivity.this, "Data saved!",
                                    Toast.LENGTH_LONG).show();

                            String uniqueID = UUID.randomUUID().toString();
                            course.setCourseId(uniqueID);
                            course.setCourseName(courseName.getText().toString());
                            course.setLecturerId(lecId);
                            course.setStartDate(startDate.getText().toString());
                            course.setEndDate(endDate.getText().toString());
                            course.setImage(downloadUri.toString());


                            databaseReference.child(uniqueID).setValue(course);
                            Intent i=new Intent(AddCourseActivity.this, MainActivity.class);
                            i.putExtra("ID",lecId);
                            i.putExtra("Email",email);
                            startActivity(i);
                        } else {
                            Toast.makeText(AddCourseActivity.this, "Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } else if (TextUtils.isEmpty(courseName.getText())) {
            courseName.setError("please enter course name");
            courseName.requestFocus();
            return;
        } else {
            Toast.makeText(this,"All Fields are required",Toast.LENGTH_SHORT).show();
        }
    }
}

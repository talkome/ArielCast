package com.example.arielcast;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.arielcast.firebase.model.dataObject.Course;
import com.squareup.picasso.Picasso;

public class MyHolder extends RecyclerView.ViewHolder {
    ImageView mImageView;
    TextView mTitle, mDes;

    public MyHolder(@NonNull View itemView) {
        super(itemView);
        this.mImageView = itemView.findViewById(R.id.lecture_image);
        this.mTitle = itemView.findViewById(R.id.textViewMain);
        this.mDes = itemView.findViewById(R.id.textViewSub);


    }

}

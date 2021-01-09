package com.example.arielcast;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView;
    View view;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imagelogo);
        textView = itemView.findViewById(R.id.textViewMain);
        view = itemView;
    }

    public void setImage(String image)
    {
       // Picasso.load(Uri.parse(image)).into(imageView);
    }
}

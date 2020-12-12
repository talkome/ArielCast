//package com.example.arielcast.firebase.model.dataObject;
//
//import android.app.Activity;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import com.example.arielcast.R;
//
//import java.util.List;
//
//public class AllLectureAdapter extends ArrayAdapter<LectureObj> {
//    Context context;
//    List<LectureObj> objects;
//
//    public AllLectureAdapter(Context context, int resource, int textViewResourceId, List<LectureObj> objects) {
//        super(context, resource, textViewResourceId, objects);
//        this.context=context;
//        this.objects=objects;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
//        View view = layoutInflater.inflate(R.layout.custom_post, parent, false);
//        TextView tvTitle = (TextView)view.findViewById(R.id.tvTitle);
//        LectureObj temp = objects.get(position);
//        tvTitle.setText(temp.getName());
//        return view;
//    }
//}

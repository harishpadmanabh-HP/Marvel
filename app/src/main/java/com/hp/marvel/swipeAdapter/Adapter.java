package com.hp.marvel.swipeAdapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.hp.marvel.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends PagerAdapter {

    ArrayList<String> name;

    ArrayList<String>imageurl;
    private LayoutInflater layoutInflater;
    private Context context;

    public Adapter(ArrayList<String> name, ArrayList<String> imageurl, Context context) {
        this.name = name;
        this.imageurl = imageurl;
        this.context = context;
    }

    @Override
    public int getCount() {

        return name.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item, container, false);

        ImageView imageView;
        TextView title;
//
        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);

//        imageView.setImageResource(models.get(position). getImage());

        title.setText(name.get(position));
        Glide.with(context).load(imageurl.get(position).trim()).into(imageView);



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}

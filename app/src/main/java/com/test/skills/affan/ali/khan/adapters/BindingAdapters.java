package com.test.skills.affan.ali.khan.adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class BindingAdapters {

    @BindingAdapter("android:imageUrl")
    public static void loadImage(View view, String url) {
        ImageView imageView = (ImageView) view;
        Glide.with(view.getContext()).load(url).into(imageView);
    }
}
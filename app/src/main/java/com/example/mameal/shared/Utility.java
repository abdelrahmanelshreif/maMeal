package com.example.mameal.shared;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mameal.R;

public class Utility {


    public static void showToast(Context context , String text){
        Toast.makeText(context,text, LENGTH_SHORT).show();
    }

    public static void loadImage(Fragment fragment, String imageUrl, ImageView imageHolder) {
        Glide.with(fragment)
                .load(imageUrl)
                .placeholder(R.drawable.default_menu_image_placeholder)
                .error(R.drawable.default_menu_image_placeholder)
                .into(imageHolder);
    }
}

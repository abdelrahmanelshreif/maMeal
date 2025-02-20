package com.example.mameal.utils;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.widget.Toast;

public class Utility {


    public static void showToast(Context context , String text){
        Toast.makeText(context,text, LENGTH_SHORT).show();
    }
}

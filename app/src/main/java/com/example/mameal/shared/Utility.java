package com.example.mameal.shared;

import static android.widget.Toast.LENGTH_SHORT;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.mameal.R;

import java.util.HashMap;
import java.util.Map;

public class Utility {


    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, LENGTH_SHORT).show();
    }

    public static void loadImage(Fragment fragment, String imageUrl, ImageView imageHolder) {
        Glide.with(fragment)
                .load(imageUrl)
                .placeholder(R.drawable.default_menu_image_placeholder)
                .error(R.drawable.default_menu_image_placeholder)
                .into(imageHolder);
    }

    public static void showAlert(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    public static void showConfirmationAlert(
            Context context,
            String title,
            String message,
            DialogInterface.OnClickListener positiveAction
    ) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", positiveAction)
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    public static String buildFlagUrl(String strArea) {
        Map<String, String> countryAbbreviations = new HashMap<>();

        countryAbbreviations.put("American", "us");
        countryAbbreviations.put("British", "gb");
        countryAbbreviations.put("Canadian", "ca");
        countryAbbreviations.put("Chinese", "cn");
        countryAbbreviations.put("Croatian", "hr");
        countryAbbreviations.put("Dutch", "nl");
        countryAbbreviations.put("Egyptian", "eg");
        countryAbbreviations.put("Filipino", "ph");
        countryAbbreviations.put("French", "fr");
        countryAbbreviations.put("Greek", "gr");
        countryAbbreviations.put("Indian", "in");
        countryAbbreviations.put("Irish", "ie");
        countryAbbreviations.put("Italian", "it");
        countryAbbreviations.put("Jamaican", "jm");
        countryAbbreviations.put("Japanese", "jp");
        countryAbbreviations.put("Kenyan", "ke");
        countryAbbreviations.put("Malaysian", "my");
        countryAbbreviations.put("Mexican", "mx");
        countryAbbreviations.put("Moroccan", "ma");
        countryAbbreviations.put("Norwegian", "no");
        countryAbbreviations.put("Polish", "pl");
        countryAbbreviations.put("Portuguese", "pt");
        countryAbbreviations.put("Russian", "ru");
        countryAbbreviations.put("Spanish", "es");
        countryAbbreviations.put("Thai", "th");
        countryAbbreviations.put("Tunisian", "tn");
        countryAbbreviations.put("Turkish", "tr");
        countryAbbreviations.put("Ukrainian", "ua");
        countryAbbreviations.put("Uruguayan", "uy");
        countryAbbreviations.put("Vietnamese", "vn");

        String countryCode = countryAbbreviations.getOrDefault(strArea, "unknown");
        return "https://www.themealdb.com/images/icons/flags/big/64/" + countryCode + ".png";

    }


    public static String ingredientThumbGenerator(String ingredientTitle) {
        String formattedIngredientName = ingredientTitle.replace(" " , "%20");
        return "http://www.themealdb.com/images/ingredients/" + formattedIngredientName + "-Small.png";

    }

}

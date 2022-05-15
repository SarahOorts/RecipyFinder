package com.example.recipyfinder;

import static com.example.recipyfinder.MainActivity.EXTRA_CATEGORY;
import static com.example.recipyfinder.MainActivity.EXTRA_ID;
import static com.example.recipyfinder.MainActivity.EXTRA_RECIPE;
import static com.example.recipyfinder.MainActivity.EXTRA_URL;
import static com.example.recipyfinder.MainActivity.EXTRA_NAME;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String mealName = intent.getStringExtra(EXTRA_NAME);
        String mealCategory = intent.getStringExtra(EXTRA_CATEGORY);
        int mealId = intent.getIntExtra(EXTRA_ID, 0);
        String recipe = intent.getStringExtra(EXTRA_RECIPE);


        ImageView imageview = findViewById(R.id.image_view);
        TextView textViewMealName = findViewById(R.id.text_view);
        TextView textViewMealCategory = findViewById(R.id.text_view_category);
        TextView textViewMealId = findViewById(R.id.text_view_id);
        TextView textViewRecipe = findViewById(R.id.text_view_recipe);

        Picasso.get().load(imageUrl).fit().centerInside().into(imageview);
        textViewMealName.setText(mealName);
        textViewMealCategory.setText("Meal category: " + mealCategory);
        textViewMealId.setText("Meal id: " + mealId);
        textViewRecipe.setText("Recipe: " + recipe);
    }
}

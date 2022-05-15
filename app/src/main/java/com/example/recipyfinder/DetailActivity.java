package com.example.recipyfinder;

import static com.example.recipyfinder.MainActivity.EXTRA_CATEGORY;
import static com.example.recipyfinder.MainActivity.EXTRA_INGREDIENTS;
import static com.example.recipyfinder.MainActivity.EXTRA_RECIPE;
import static com.example.recipyfinder.MainActivity.EXTRA_SERVINGS;
import static com.example.recipyfinder.MainActivity.EXTRA_URL;
import static com.example.recipyfinder.MainActivity.EXTRA_NAME;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    TextView textViewMealServings;

    ArrayList<Ingredient> recept_array = new ArrayList<>();
    ArrayList<TextView> tv_array = new ArrayList<>();
    int amount_of_people = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String mealName = intent.getStringExtra(EXTRA_NAME);
        String mealCategory = intent.getStringExtra(EXTRA_CATEGORY);
        //int mealId = intent.getIntExtra(EXTRA_ID, 0);
        String recipe = intent.getStringExtra(EXTRA_RECIPE);
        ArrayList<String> mealIngredients = intent.getStringArrayListExtra(EXTRA_INGREDIENTS);
        double persons = Double.parseDouble(intent.getStringExtra(EXTRA_SERVINGS));
        int servings = (int)persons;
        //Log.d("persons", String.valueOf(servings));

        ImageView imageview = findViewById(R.id.image_view);
        TextView textViewMealName = findViewById(R.id.text_view);
        TextView textViewMealCategory = findViewById(R.id.text_view_category);
        textViewMealServings = findViewById(R.id.text_view_servings);
        TextView textViewRecipe = findViewById(R.id.text_view_recipe);
        Button plus_btn = findViewById(R.id.btnplus);
        Button min_btn = findViewById(R.id.btnmin);
        TextView ingr_tv;

        ViewGroup layout = (ViewGroup) findViewById(R.id.rootlayout);

        Picasso.get().load(imageUrl).fit().centerInside().into(imageview);
        textViewMealName.setText(mealName);
        textViewMealCategory.setText("Meal category: " + mealCategory);
        textViewMealServings.setText("Servings: " + servings);
        textViewRecipe.setText("Recipe: " + recipe);


        JSONArray Ingredients = new JSONArray(mealIngredients);
        //Log.d("ingredient", String.valueOf(Ingredients));
        for(int o = 0; o < Ingredients.length(); o++){
            String ingr = null;
            try {
                ingr = Ingredients.getString(o);
                JSONObject number = new JSONObject(ingr);
                String quantity = number.getString("quantity");
                String measure = number.getString("measure");
                String food = number.getString("food");
                Log.d("ingredient", quantity);
                Log.d("ingredient", measure);
                Log.d("ingredient", food);
                recept_array.add(new Ingredient(quantity, measure, food, servings));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for(int i = 0; i < recept_array.size(); i++){
            TextView tv = new TextView(this); // maak een nieuwe (lege) TextView aan
            layout.addView(tv, i+6);  // i+3 is de volgorde van de TextView in de UI (onder het aantal personen)
            tv_array.add(tv);

            final Ingredient current_ingredient = recept_array.get(i);
        }

        update_ui();

        plus_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                amount_of_people = amount_of_people + 1;
                DetailActivity.this.update_ui();
            }
        });

        min_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (amount_of_people == 1) return;
                amount_of_people = amount_of_people - 1;
                DetailActivity.this.update_ui();
            }
        });
    }

    void update_ui(){
        String persons = String.format("Servings: %d", amount_of_people);
        textViewMealServings.setText(persons);

        for (int i = 0; i<recept_array.size(); i++) {
            TextView tv = tv_array.get(i);  // pak de i-de TextView
            Ingredient ing = recept_array.get(i); // pak het i-de ingrediënt
            tv.setText(ing.getquantity(amount_of_people));  // converteer het ingrediënt naar text
        }
    }
}

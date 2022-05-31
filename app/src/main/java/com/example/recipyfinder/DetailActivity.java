package com.example.recipyfinder;

import static com.example.recipyfinder.MainActivity.EXTRA_CAL;
import static com.example.recipyfinder.MainActivity.EXTRA_CATEGORY;
import static com.example.recipyfinder.MainActivity.EXTRA_INGREDIENTS;
import static com.example.recipyfinder.MainActivity.EXTRA_SERVINGS;
import static com.example.recipyfinder.MainActivity.EXTRA_TIME;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity{
    public static final String EXTRA_SERVINGSIZE = "servingsize";
    TextView textViewMealServings;
    TextView textViewcal;
    ArrayList<Ingredient> recept_array = new ArrayList<>();
    ArrayList<TextView> tv_array = new ArrayList<>();
    int amount_of_people = 1;
    TextView tv;
    String cal;
    String servings;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String mealName = intent.getStringExtra(EXTRA_NAME);
        String mealCategory = intent.getStringExtra(EXTRA_CATEGORY);
        ArrayList<String> mealIngredients = intent.getStringArrayListExtra(EXTRA_INGREDIENTS);
        String time = intent.getStringExtra(EXTRA_TIME);
        servings = intent.getStringExtra(EXTRA_SERVINGS);
        cal = intent.getStringExtra(EXTRA_CAL);

        ImageView imageview = findViewById(R.id.image_view);
        TextView textViewMealName = findViewById(R.id.text_view);
        TextView textViewMealCategory = findViewById(R.id.text_view_category);
        textViewMealServings = findViewById(R.id.text_view_servings);
        textViewcal = findViewById(R.id.text_view_calories);
        TextView textViewt = findViewById(R.id.text_view_time);
        Button plus_btn = findViewById(R.id.btnplus);
        Button min_btn = findViewById(R.id.btnmin);
        Button share_btn = findViewById(R.id.btnshare);

        ViewGroup layout = (ViewGroup) findViewById(R.id.rootlayout);

        Picasso.get().load(imageUrl).fit().centerInside().into(imageview);
        textViewMealName.setText(mealName);
        textViewMealCategory.setText("Meal category: " + mealCategory);
        textViewMealServings.setText("Servings: " + servings);
        textViewt.setText("Time: " + time);
        textViewcal.setText(Ingredient.getCalories(cal, servings, amount_of_people));

        for(int o = 0; o < mealIngredients.size(); o++){
            String ingr = null;
            try {
                ingr = mealIngredients.get(o);
                JSONObject number = new JSONObject(ingr);
                String quantity = number.getString("quantity");
                String measure = number.getString("measure");
                String food = number.getString("food");
                recept_array.add(new Ingredient(quantity, measure, food, servings));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for(int i = 0; i < recept_array.size(); i++){
            TextView tv = new TextView(this);
            layout.addView(tv, i);
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

        share_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                StringBuilder list = new StringBuilder();

                for (int i = 0; i< recept_array.size(); i++) {
                    Ingredient ingredients = recept_array.get(i);
                    String in = ingredients.getquantity(amount_of_people) + ", ";
                    list.append(in);
                }
                shareIntent.putExtra(Intent.EXTRA_TEXT, list.toString());
                startActivity(Intent.createChooser(shareIntent, "Share ingredients using"));
            }
        });
    }

    void update_ui(){
        String persons = String.format("Servings: %d", amount_of_people);
        textViewMealServings.setText(persons);
        textViewcal.setText(Ingredient.getCalories(cal, servings, amount_of_people));

        for (int i = 0; i< recept_array.size(); i++) {
            tv = tv_array.get(i);
            Ingredient ing = recept_array.get(i);
            tv.setText(ing.getquantity(amount_of_people));
            String servingsize = ing.getquantity(amount_of_people);
            tv_array.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent ingredientIntent = new Intent(DetailActivity.this, IngredientActivity.class);
                    ingredientIntent.putExtra(EXTRA_SERVINGSIZE, servingsize);
                    startActivity(ingredientIntent);
                }
            });
        }
    }
}

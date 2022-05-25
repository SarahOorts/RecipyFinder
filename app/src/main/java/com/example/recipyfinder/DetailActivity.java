package com.example.recipyfinder;

import static com.example.recipyfinder.MainActivity.EXTRA_CATEGORY;
import static com.example.recipyfinder.MainActivity.EXTRA_INGREDIENTS;
import static com.example.recipyfinder.MainActivity.EXTRA_LIST;
import static com.example.recipyfinder.MainActivity.EXTRA_POS;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_SERVINGSIZE = "servingsize";
    //private ArrayList<MainItem> mMainList;
    TextView textViewMealServings;
    ArrayList<Ingredient> recept_array = new ArrayList<>();
    ArrayList<TextView> tv_array = new ArrayList<>();
    int amount_of_people = 1;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String recipe = intent.getStringExtra(EXTRA_RECIPE);
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String mealName = intent.getStringExtra(EXTRA_NAME);
        String mealCategory = intent.getStringExtra(EXTRA_CATEGORY);
        ArrayList<String> dinner = intent.getStringArrayListExtra(EXTRA_LIST);
        ArrayList<String> mealIngredients = intent.getStringArrayListExtra(EXTRA_INGREDIENTS);
        String servings = intent.getStringExtra(EXTRA_SERVINGS);
        String pos = intent.getStringExtra(EXTRA_POS);

        ImageView imageview = findViewById(R.id.image_view);
        TextView textViewMealName = findViewById(R.id.text_view);
        TextView textViewMealCategory = findViewById(R.id.text_view_category);
        textViewMealServings = findViewById(R.id.text_view_servings);
        Button plus_btn = findViewById(R.id.btnplus);
        Button min_btn = findViewById(R.id.btnmin);
        Button share_btn = findViewById(R.id.btnshare);

        ViewGroup layout = (ViewGroup) findViewById(R.id.rootlayout);

        Picasso.get().load(imageUrl).fit().centerInside().into(imageview);
        textViewMealName.setText(mealName);
        textViewMealCategory.setText("Meal category: " + mealCategory);
        textViewMealServings.setText("Servings: " + servings);

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

        for (int i = 0; i< dinner.size(); i++) {
            String smth = dinner.get(i);
            String[] smtharr = smth.split(", ", 0);
            //Log.d("list", String.valueOf(smtharr[2]));
            for(int o = 0; o < smtharr.length; o++){
                String s = String.format("%s", smtharr[o]);
                String p = String.format("%s", pos);
                //Log.d("equal", String.valueOf(s.equals(p)));
                if(s.equals(p)){
                    Log.d("found", p);
                    /*String meal = p.getmMealName();
                    Log.d("rlist", meal);*/
                }
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
                Log.d("fo", list.toString());
                startActivity(Intent.createChooser(shareIntent, "Share ingredients using"));
            }
        });
    }

    void update_ui(){
        String persons = String.format("Servings: %d", amount_of_people);
        textViewMealServings.setText(persons);

        for (int i = 0; i< recept_array.size(); i++) {
            tv = tv_array.get(i);
            Ingredient ing = recept_array.get(i);
            tv.setText(ing.getquantity(amount_of_people));
            String servingsize = ing.getquantity(amount_of_people);
            //Log.d("tv", String.valueOf(tv_array.get(i)));
            //Log.d("ing", String.valueOf(ing.getquantity(amount_of_people)));

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

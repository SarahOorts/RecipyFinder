package com.example.recipyfinder;

import android.content.Intent;
import android.os.Bundle;
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
    //initialiseer public static string om data via intent door te geven
    public static final String EXTRA_SERVINGSIZE = "servingsize";

    //initialiseer variabele
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
        //initialiseer content van de parent
        super.onCreate(savedInstanceState);
        //initialiseer layout
        setContentView(R.layout.activity_detail);

        //vraag data op vanuit een intent
        Intent intent = getIntent();
        int id = intent.getIntExtra(MainActivity.EXTRA_ID, 0);

        //voeg data van intent toe aan variabele via public variabele
        String imageUrl = MainActivity.mMainList.get(id).getmImageUrl();
        String mealName = MainActivity.mMainList.get(id).getmMealName();
        String mealCategory = MainActivity.mMainList.get(id).getmMealCategory();
        ArrayList<String> mealIngredients = MainActivity.mMainList.get(id).getmIngredients();
        String time = MainActivity.mMainList.get(id).getmTime();
        servings = MainActivity.mMainList.get(id).getmServings();
        cal = MainActivity.mMainList.get(id).getmCal();

        //link buttons en textviews aan layout
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

        //voeg variabele toe aan layout
        Picasso.get().load(imageUrl).fit().centerInside().into(imageview);
        textViewMealName.setText(mealName);
        textViewMealCategory.setText("Meal category: " + mealCategory);
        textViewMealServings.setText("Servings: " + servings);
        textViewt.setText("Time: " + time);
        textViewcal.setText(Ingredient.getCalories(cal, servings, amount_of_people));

        //voeg ingredienten vanuit JSONArray toe aan Ingredient class
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

        //voeg nieuwe textview toe voor het aantal ingrediënten
        for(int i = 0; i < recept_array.size(); i++){
            TextView tv = new TextView(this);
            layout.addView(tv, i);
            tv_array.add(tv);
            final Ingredient current_ingredient = recept_array.get(i);
        }

        //update user interface
        update_ui();

        //voeg meer personen toe aan recept
        plus_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                amount_of_people = amount_of_people + 1;
                DetailActivity.this.update_ui();
            }
        });

        //verminder personen voor het recept
        min_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (amount_of_people == 1) return;
                amount_of_people = amount_of_people - 1;
                DetailActivity.this.update_ui();
            }
        });

        //deel ingredienten via intent
        share_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                StringBuilder list = new StringBuilder();

                for (int i = 0; i< recept_array.size(); i++) {
                    Ingredient ingredients = recept_array.get(i);
                    String in = ingredients.getquantity(amount_of_people) + ", \n";
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

        //verander hoeveelheid ingredienten naar juiste hoeveelheid en voeg dan toe aan de layout
        for (int i = 0; i< recept_array.size(); i++) {
            tv = tv_array.get(i);
            Ingredient ing = recept_array.get(i);
            tv.setText(ing.getquantity(amount_of_people));
            String servingsize = ing.getquantity(amount_of_people);
            tv_array.get(i).setOnClickListener(new View.OnClickListener() {
                //bij klik verplaats naar andere activity
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

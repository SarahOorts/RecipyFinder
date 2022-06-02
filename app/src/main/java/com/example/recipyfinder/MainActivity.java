package com.example.recipyfinder;

import static com.example.recipyfinder.CuisineActivity.EXTRA_TYPE;
import static com.example.recipyfinder.MealActivity.EXTRA_MEAL;
import static com.example.recipyfinder.WeatherActivity.EXTRA_CITY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainAdapter.OnItemClickListener {
    //initialiseer public static string om data via intent door te geven
    public static final String EXTRA_ID = "id";

    //initialiseer recyclerview, view adapter, volley requestqueue en extra variabele
    private RecyclerView mRecyclerView;
    private MainAdapter mMainAdapter;
    //arraylist is public static om makkelijk mee te geven in de intent
    public static ArrayList<MainItem> mMainList;
    private RequestQueue mRequestQueue;
    String url;
    String href;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialiseer content van de parent
        super.onCreate(savedInstanceState);
        //initialiseer layout
        setContentView(R.layout.activity_main);

        //voeg layout element toe aan de recyclerview
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //zet recyclerview en volley elementen klaar
        mMainList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        mMainAdapter = new MainAdapter(MainActivity.this, mMainList);
        mRecyclerView.setAdapter(mMainAdapter);
        mMainAdapter.setOnItemClickListener(MainActivity.this);
        mRequestQueue = Volley.newRequestQueue(this);

        //vraag data op vanuit een intent
        Intent intent = getIntent();
        String cuisineType = intent.getStringExtra(EXTRA_TYPE);
        String mealType = intent.getStringExtra(EXTRA_MEAL);
        String temp = intent.getStringExtra(EXTRA_CITY);

        //link buttons en edittext aan layout
        Button mSearchbtn = findViewById(R.id.search_btn);
        EditText searchfield = findViewById(R.id.input);
        Button cuisine = findViewById(R.id.cuisine_btn);
        Button searchAll = findViewById(R.id.searchall_btn);
        Button weather = findViewById(R.id.weather_btn);
        Button mealtype = findViewById(R.id.meal_btn);
        Button nxt = findViewById(R.id.next_btn);

        //stel start api call in als search leeg is
        if(searchfield.getText().toString().matches("")){
            String orange = "orange";
            getDishes(orange, cuisineType, mealType, temp);
        }

        //bij klik vraag nieuwe search op
        mSearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchterm;
                searchterm = searchfield.getText().toString();
                getDishes(searchterm, cuisineType, mealType, temp);
            }
        });

        //bij klik verplaats naar andere activity
        cuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cuisineIntent = new Intent(MainActivity.this, CuisineActivity.class);
                startActivity(cuisineIntent);
            }
        });

        //bij klik verplaats naar andere activity
        searchAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(searchIntent);
            }
        });

        //bij klik verplaats naar andere activity
        mealtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mealIntent = new Intent(MainActivity.this, MealActivity.class);
                startActivity(mealIntent);
            }
        });

        //bij klik verplaats naar andere activity
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent weatherIntent = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(weatherIntent);
            }
        });

        //bij klik vraag nieuwe data op van dezelfde search
        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDishes(href, cuisineType, mealType, temp);
            }
        });
    }

    //check of strings leeg zijn
    private boolean isNullEmpty(String str) {
        if(str == null){
            return true;
        }else if (str.isEmpty()){
            return true;
        } else{
            return false;
        }
    }

    //functie om api data op te vragen
    private void getDishes(String search, String cuisineType, String mealType, String degree){
        //zoeken bij mealtype
        if(isNullEmpty(cuisineType) && !isNullEmpty(mealType) && isNullEmpty(degree)) {
            url = "https://api.edamam.com/api/recipes/v2?type=public&q=" + search + "&app_id=ac2b3ec2&app_key=6e4584b709c2b12eaa126c832cd800a2&mealType=" + mealType;
        } else if(isNullEmpty(mealType) && !isNullEmpty(cuisineType) && isNullEmpty(degree)) { //zoeken bij cuisinetype
            url = "https://api.edamam.com/api/recipes/v2?type=public&q=" + search + "&app_id=ac2b3ec2&app_key=6e4584b709c2b12eaa126c832cd800a2&cuisineType=" + cuisineType;
        } else if(isNullEmpty(mealType) && isNullEmpty(cuisineType) && !isNullEmpty(degree)){ //zoeken bij temperatuur
            int temp = Integer.parseInt(degree);
            if(temp < 0){
                url = "https://api.edamam.com/api/recipes/v2?type=public&q=hot%20chocolate%20&app_id=ac2b3ec2&app_key=6e4584b709c2b12eaa126c832cd800a2";
            } else if(temp > 0 && temp < 5){
                url = "https://api.edamam.com/api/recipes/v2?type=public&q=stew&app_id=ac2b3ec2&app_key=6e4584b709c2b12eaa126c832cd800a2";
            } else if(temp > 5 && temp < 10){
                url = "https://api.edamam.com/api/recipes/v2?type=public&q=risotto&app_id=ac2b3ec2&app_key=6e4584b709c2b12eaa126c832cd800a2";
            } else if(temp > 10 && temp < 15){
                url = "https://api.edamam.com/api/recipes/v2?type=public&q=pasta&app_id=ac2b3ec2&app_key=6e4584b709c2b12eaa126c832cd800a2";
            } else if(temp > 15 && temp < 20){
                url = "https://api.edamam.com/api/recipes/v2?type=public&q=sushi&app_id=ac2b3ec2&app_key=6e4584b709c2b12eaa126c832cd800a2";
            } else if(temp > 20 && temp < 25){
                url = "https://api.edamam.com/api/recipes/v2?type=public&q=salad&app_id=ac2b3ec2&app_key=6e4584b709c2b12eaa126c832cd800a2";
            } else if(temp > 25 && temp < 30){
                url = "https://api.edamam.com/api/recipes/v2?type=public&q=cocktail&app_id=ac2b3ec2&app_key=6e4584b709c2b12eaa126c832cd800a2";
            } else if(temp > 30 && temp < 35){
                url = "https://api.edamam.com/api/recipes/v2?type=public&q=ice%20cream%20&app_id=ac2b3ec2&app_key=6e4584b709c2b12eaa126c832cd800a2";
            }
        } else if(isNullEmpty(mealType) && isNullEmpty(cuisineType) && isNullEmpty(degree)){ //zoeken bij zoekbalk term
            if(search.contains("https")){ //volgende pagina zoekbalk term
                url = search;
            } else{
                url = "https://api.edamam.com/api/recipes/v2?type=public&q="+search+"&app_id=ac2b3ec2&app_key=6e4584b709c2b12eaa126c832cd800a2";
            }
        }
        //maakt recyclerview leeg vooraleer nieuwe data in te stoppen
        mMainList.clear();

        //api call (1 = methode, 2 = url, 3 = request, 4 = success, 5 =  error)
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray jsonArray = response.getJSONArray("hits");

                            //loop door de verschillende recepten
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject mealhit = jsonArray.getJSONObject(i);
                                JSONObject meal = mealhit.getJSONObject("recipe");
                                JSONArray cuisineType = meal.getJSONArray("cuisineType");
                                String mealCategory = cuisineType.getString(0);
                                String cal = meal.getString("calories");
                                String t = meal.getString("totalTime");

                                JSONArray mealIngredients = meal.getJSONArray("ingredients");
                                ArrayList<String> ingr = new ArrayList<>();
                                //loop door de verschillende ingrediënten
                                for(int o = 0; o < mealIngredients.length(); o++){
                                    String ingrs = mealIngredients.getString(o);
                                    ingr.add(ingrs);
                                }

                                String mealName = meal.getString("label");
                                String imageUrl = meal.getString("image");
                                int mealId = 0;
                                String recipe = meal.getString("url");
                                String servings = meal.getString("yield");

                                //api link voor volgende 20 recepten
                                JSONObject links = response.getJSONObject("_links");
                                JSONObject next = links.getJSONObject("next");
                                href = next.getString("href");

                                //voeg opgevraagde data toe aan de lijst
                                mMainList.add(new MainItem(imageUrl, mealName, mealCategory, recipe, ingr, servings, cal, t));
                            }
                            //laat de recyclerview adapter weten of data is veranderd en de pagina herladen moet worden
                            mMainAdapter.notifyDataSetChanged();
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    //bij klik stuurt naar detail van recept
    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        MainItem clickedItem = mMainList.get(position);
        //geeft positie in de lijst weer
        detailIntent.putExtra(EXTRA_ID, position);
        startActivity(detailIntent);
    }
}
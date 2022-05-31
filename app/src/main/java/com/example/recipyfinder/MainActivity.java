package com.example.recipyfinder;

import static com.example.recipyfinder.CuisineActivity.EXTRA_TYPE;
import static com.example.recipyfinder.MealActivity.EXTRA_MEAL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    public static final String EXTRA_URL = "imageUrl";
    public static final String EXTRA_NAME = "mealName";
    public static final String EXTRA_CATEGORY = "mealCategory";
    public static final String EXTRA_RECIPE = "recipe";
    public static final String EXTRA_INGREDIENTS = "ingredients";
    public static final String EXTRA_SERVINGS = "servings";

    private RecyclerView mRecyclerView;
    private MainAdapter mMainAdapter;
    private ArrayList<MainItem> mMainList;
    private RequestQueue mRequestQueue;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMainList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        String cuisineType = intent.getStringExtra(EXTRA_TYPE);
        String mealType = intent.getStringExtra(EXTRA_MEAL);

        Button mSearchbtn = findViewById(R.id.search_btn);
        EditText searchfield = findViewById(R.id.input);
        Button cuisine = findViewById(R.id.cuisine_btn);
        Button searchAll = findViewById(R.id.searchall_btn);
        Button weather = findViewById(R.id.weather_btn);
        Button mealtype = findViewById(R.id.meal_btn);


        if(searchfield.getText().toString().matches("")){
            String beef = "beef";
            getDishes(beef, cuisineType, mealType);
        }

        mSearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchterm;
                searchterm = searchfield.getText().toString();
                if(searchterm.matches("")) {
                    searchterm = "beef";
                }
                Log.d("search", searchterm);

                getDishes(searchterm, cuisineType, mealType);
            }
        });

        cuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cuisineIntent = new Intent(MainActivity.this, CuisineActivity.class);
                startActivity(cuisineIntent);
            }
        });

        searchAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(searchIntent);
            }
        });

        mealtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mealIntent = new Intent(MainActivity.this, MealActivity.class);
                startActivity(mealIntent);
            }
        });
    }

    private boolean isNullEmpty(String str) {
        if(str == null){
            return true;
        }else if (str.isEmpty()){
            return true;
        } else{
            return false;
        }
    }

    private void getDishes(String search, String cuisineType, String mealType){
        if(isNullEmpty(cuisineType) && !isNullEmpty(mealType)) {
            url = "https://api.edamam.com/api/recipes/v2?type=public&q=" + search + "&app_id=ac2b3ec2&app_key=6e4584b709c2b12eaa126c832cd800a2&mealType=" + mealType;
        } else if(isNullEmpty(mealType) && !isNullEmpty(cuisineType)) {
            url = "https://api.edamam.com/api/recipes/v2?type=public&q=" + search + "&app_id=ac2b3ec2&app_key=6e4584b709c2b12eaa126c832cd800a2&cuisineType=" + cuisineType;
        } else if(isNullEmpty(mealType) && isNullEmpty(cuisineType)){
            url = "https://api.edamam.com/api/recipes/v2?type=public&q="+search+"&app_id=ac2b3ec2&app_key=6e4584b709c2b12eaa126c832cd800a2";
        }
        Log.d("search_url", url);
        mMainList.clear();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray jsonArray = response.getJSONArray("hits");

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject mealhit = jsonArray.getJSONObject(i);
                                JSONObject meal = mealhit.getJSONObject("recipe");
                                JSONArray cuisineType = meal.getJSONArray("cuisineType");
                                String mealCategory = cuisineType.getString(0);

                                JSONArray mealIngredients = meal.getJSONArray("ingredients");
                                ArrayList<String> ingr = new ArrayList<String>();
                                for(int o = 0; o < mealIngredients.length(); o++){
                                    String ingrs = mealIngredients.getString(o);
                                    ingr.add(ingrs);
                                }

                                String mealName = meal.getString("label");
                                String imageUrl = meal.getString("image");
                                int mealId = 0;
                                String recipe = meal.getString("url");
                                String servings = meal.getString("yield");

                                mMainList.add(new MainItem(imageUrl, mealName, mealCategory, recipe, ingr, servings));
                            }

                            mMainAdapter = new MainAdapter(MainActivity.this, mMainList);
                            mRecyclerView.setAdapter(mMainAdapter);
                            mMainAdapter.setOnItemClickListener(MainActivity.this);

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

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        MainItem clickedItem = mMainList.get(position);

        detailIntent.putExtra(EXTRA_URL, clickedItem.getmImageUrl());
        detailIntent.putExtra(EXTRA_NAME, clickedItem.getmMealName());
        detailIntent.putExtra(EXTRA_CATEGORY, clickedItem.getmMealCategory());
        detailIntent.putExtra(EXTRA_RECIPE, clickedItem.getmRecipe());
        detailIntent.putStringArrayListExtra(EXTRA_INGREDIENTS, clickedItem.getmIngredients());
        detailIntent.putExtra(EXTRA_SERVINGS, clickedItem.getmServings());

        startActivity(detailIntent);
    }
}
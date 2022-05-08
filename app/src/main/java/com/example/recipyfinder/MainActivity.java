package com.example.recipyfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MainAdapter mMainAdapter;
    private ArrayList<MainItem> mMainList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMainList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        getDishes();
    }

    private void getDishes(){
        String url = "https://www.themealdb.com/api/json/v1/1/search.php?s=chicken";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray jsonArray = response.getJSONArray("meals");

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject meal = jsonArray.getJSONObject(i);

                                String mealName = meal.getString("strMeal");
                                String imageUrl = meal.getString("strMealThumb");
                                int mealId = meal.getInt("idMeal");

                                mMainList.add(new MainItem(imageUrl, mealName, mealId));
                            }

                            mMainAdapter = new MainAdapter(MainActivity.this, mMainList);
                            mRecyclerView.setAdapter(mMainAdapter);

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
}
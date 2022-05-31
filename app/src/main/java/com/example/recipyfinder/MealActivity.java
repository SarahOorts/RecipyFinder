package com.example.recipyfinder;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MealActivity extends AppCompatActivity implements CuisineAdapter.OnItemClickListener {
    private RecyclerView cRecyclerView;
    private CuisineAdapter cCuisineAdapter;
    private ArrayList<CuisineItem> cCuisineList;
    public static final String EXTRA_MEAL = "meal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine);

        cRecyclerView = findViewById(R.id.recycler_view);
        cRecyclerView.setHasFixedSize(true);
        cRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        cCuisineList = new ArrayList<>();
        cCuisineList.add(new CuisineItem("Breakfast"));
        cCuisineList.add(new CuisineItem("Dinner"));
        cCuisineList.add(new CuisineItem("Lunch"));
        cCuisineList.add(new CuisineItem("Snack"));
        cCuisineList.add(new CuisineItem("Tea time"));

        cCuisineAdapter = new CuisineAdapter(MealActivity.this, cCuisineList);
        cRecyclerView.setAdapter(cCuisineAdapter);
        cCuisineAdapter.setOnItemClickListener(MealActivity.this);
    }

    @Override
    public void onItemClick(int position) {
        Intent mealIntent = new Intent(MealActivity.this, MainActivity.class);
        CuisineItem clickedItem = cCuisineList.get(position);
        mealIntent.putExtra(EXTRA_MEAL, clickedItem.getcType());
        startActivity(mealIntent);
    }
}

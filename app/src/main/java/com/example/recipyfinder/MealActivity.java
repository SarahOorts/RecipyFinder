package com.example.recipyfinder;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MealActivity extends AppCompatActivity implements CuisineAdapter.OnItemClickListener {
    //initialiseer recyclerview, view adapter en extra variabele
    private RecyclerView cRecyclerView;
    private CuisineAdapter cCuisineAdapter;
    private ArrayList<CuisineItem> cCuisineList;
    public static final String EXTRA_MEAL = "meal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialiseer content van de parent
        super.onCreate(savedInstanceState);
        //initialiseer layout
        setContentView(R.layout.activity_cuisine);

        //voeg layout element toe aan de recyclerview
        cRecyclerView = findViewById(R.id.recycler_view);
        cRecyclerView.setHasFixedSize(true);
        cRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //maak nieuwe lijst aan en voeg items toe aan de lijst
        cCuisineList = new ArrayList<>();
        cCuisineList.add(new CuisineItem("Breakfast"));
        cCuisineList.add(new CuisineItem("Dinner"));
        cCuisineList.add(new CuisineItem("Lunch"));
        cCuisineList.add(new CuisineItem("Snack"));

        //voeg adapter toe aan de layout en initialiseer een onclick
        cCuisineAdapter = new CuisineAdapter(MealActivity.this, cCuisineList);
        cRecyclerView.setAdapter(cCuisineAdapter);
        cCuisineAdapter.setOnItemClickListener(MealActivity.this);
    }

    //bij klik stuur door naar andere activity
    @Override
    public void onItemClick(int position) {
        Intent mealIntent = new Intent(MealActivity.this, MainActivity.class);
        CuisineItem clickedItem = cCuisineList.get(position);
        mealIntent.putExtra(EXTRA_MEAL, clickedItem.getType());
        startActivity(mealIntent);
    }
}

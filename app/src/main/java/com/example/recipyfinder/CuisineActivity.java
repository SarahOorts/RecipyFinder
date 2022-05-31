package com.example.recipyfinder;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class CuisineActivity extends AppCompatActivity implements CuisineAdapter.OnItemClickListener{
    private RecyclerView cRecyclerView;
    private CuisineAdapter cCuisineAdapter;
    private ArrayList<CuisineItem> cCuisineList;
    public static final String EXTRA_TYPE = "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuisine);

        cRecyclerView = findViewById(R.id.recycler_view);
        cRecyclerView.setHasFixedSize(true);
        cRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        cCuisineList = new ArrayList<>();
        cCuisineList.add(new CuisineItem("American"));
        cCuisineList.add(new CuisineItem("Asian"));
        cCuisineList.add(new CuisineItem("British"));;
        cCuisineList.add(new CuisineItem("Caribbean"));
        cCuisineList.add(new CuisineItem("Central Europe"));
        cCuisineList.add(new CuisineItem("Chinese"));
        cCuisineList.add(new CuisineItem("Eastern Europe"));
        cCuisineList.add(new CuisineItem("French"));
        cCuisineList.add(new CuisineItem("Indian"));
        cCuisineList.add(new CuisineItem("Italian"));
        cCuisineList.add(new CuisineItem("Japanese"));
        cCuisineList.add(new CuisineItem("Kosher"));
        cCuisineList.add(new CuisineItem("Mediterranean"));
        cCuisineList.add(new CuisineItem("Mexican"));
        cCuisineList.add(new CuisineItem("Middle Eastern"));
        cCuisineList.add(new CuisineItem("Nordic"));
        cCuisineList.add(new CuisineItem("South American"));
        cCuisineList.add(new CuisineItem("South East Asian"));

        cCuisineAdapter = new CuisineAdapter(CuisineActivity.this, cCuisineList);
        cRecyclerView.setAdapter(cCuisineAdapter);
        cCuisineAdapter.setOnItemClickListener(CuisineActivity.this);
    }

    @Override
    public void onItemClick(int position) {
        Intent cuisineIntent = new Intent(CuisineActivity.this, MainActivity.class);
        CuisineItem clickedItem = cCuisineList.get(position);
        cuisineIntent.putExtra(EXTRA_TYPE, clickedItem.getcType());
        startActivity(cuisineIntent);
    }
}

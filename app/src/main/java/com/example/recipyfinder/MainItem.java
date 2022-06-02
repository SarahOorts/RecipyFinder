package com.example.recipyfinder;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainItem {
    //initialiseer alle variabele die behoren tot een mainitem
    private String mImageUrl;
    private String mMealName;
    private String mMealCategory;
    private String mRecipe;
    private ArrayList<String> mIngredients;
    private String mServings;
    private String mCal;
    private String mTime;

    public MainItem(String imageUrl, String mealName, String mealCategory, String recipe, ArrayList<String> ingredients, String servings, String cal, String time){
        mImageUrl = imageUrl;
        mMealName = mealName;
        mMealCategory = mealCategory;
        mRecipe = recipe;
        mIngredients = ingredients;
        mServings = servings;
        mCal = cal;
        mTime = time;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmMealName() {
        return mMealName;
    }

    public String getmMealCategory() { return mMealCategory;}

    public String getmRecipe(){return mRecipe;}

    public ArrayList<String> getmIngredients(){return mIngredients;}

    public String getmServings(){return mServings;}

    public String getmCal(){return mCal;}

    public String getmTime(){return mTime;}
}

package com.example.recipyfinder;

import org.json.JSONArray;

import java.util.ArrayList;

public class MainItem {
    private String mImageUrl;
    private String mMealName;
    private String mMealCategory;
    private int mMealId;
    private String mRecipe;
    private ArrayList<String> mIngredients;
    private String mServings;

    public MainItem(String imageUrl, String mealName, String mealCategory, int mealId, String recipe, ArrayList<String> ingredients, String servings){
        mImageUrl = imageUrl;
        mMealName = mealName;
        mMealCategory = mealCategory;
        mMealId = mealId;
        mRecipe = recipe;
        mIngredients = ingredients;
        mServings = servings;
    }

    public MainItem(String imageUrl, String mealName, String mealCategory, int mealId, String recipe, ArrayList<String> ingr) {
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmMealName() {
        return mMealName;
    }

    public String getmMealCategory() { return mMealCategory;}

    public int getmMealId(){return mMealId;}

    public String getmRecipe(){return mRecipe;}

    public ArrayList<String> getmIngredients(){return mIngredients;}

    public String getmServings(){return mServings;}
}

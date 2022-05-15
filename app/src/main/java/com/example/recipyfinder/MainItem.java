package com.example.recipyfinder;

public class MainItem {
    private String mImageUrl;
    private String mMealName;
    private String mMealCategory;
    private int mMealId;
    private String mRecipe;

    public MainItem( String imageUrl, String mealName, String mealCategory, int mealId, String recipe){
        mImageUrl = imageUrl;
        mMealName = mealName;
        mMealCategory = mealCategory;
        mMealId = mealId;
        mRecipe = recipe;
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
}

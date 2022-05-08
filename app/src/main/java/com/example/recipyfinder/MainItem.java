package com.example.recipyfinder;

public class MainItem {
    private String mImageUrl;
    private String mCountryCuisine;
    private int mMealId;

    public MainItem( String imageUrl, String countryCuisine, int mealId){
        mImageUrl = imageUrl;
        mCountryCuisine = countryCuisine;
        mMealId = mealId;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmCountryCuisine() {
        return mCountryCuisine;
    }

    public int getmMealId() {
        return mMealId;
    }
}

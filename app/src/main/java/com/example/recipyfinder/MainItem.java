package com.example.recipyfinder;

public class MainItem {
    private String mImageUrl;
    private String mMealName;
    private int mMealId;

    public MainItem( String imageUrl, String mealName, int mealId){
        mImageUrl = imageUrl;
        mMealName = mealName;
        mMealId = mealId;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmMealName() {
        return mMealName;
    }

    public int getmMealId() {
        return mMealId;
    }
}

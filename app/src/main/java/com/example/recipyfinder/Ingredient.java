package com.example.recipyfinder;

import android.util.Log;

public class Ingredient {
    String quantity, measure, food;
    int servings;

    Ingredient(String quantity,
               String measure,
               String food,
               int servings){
        this.quantity = quantity;
        this.measure = measure;
        this.food = food;
        this.servings = servings;
    }

    public String getquantity(int amount_of_people) {
        double amount = Double.parseDouble(quantity);
        Log.d("amount", String.valueOf(amount));
        double total1 = amount / (double) servings;
        double total2 = total1 * (double) amount_of_people;
        //Log.d("total", String.valueOf(total2));
        return String.format("%s %s %s",total2, getMeasure(), getFood());
    }

    public String getMeasure() {
        return this.measure;
    }

    public String getFood() {
        return this.food;
    }
}

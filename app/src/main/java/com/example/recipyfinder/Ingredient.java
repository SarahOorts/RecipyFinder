package com.example.recipyfinder;

import android.util.Log;

public class Ingredient {
    String quantity, measure, food, servings;

    Ingredient(String quantity,
               String measure,
               String food,
               String servings){
        this.quantity = quantity;
        this.measure = measure;
        this.food = food;
        this.servings = servings;
    }

    public String getquantity(int amount_of_people) {
        double serving = Double.parseDouble(servings);
        double amount = Double.parseDouble(quantity);
        double total1 = amount / serving;
        double total2 = total1 * (double) amount_of_people;
        return String.format("%,.2f %s %s",total2, getMeasure(), getFood());
    }

    public String getMeasure() {
        if(measure.equals("<unit>") || measure.equals("null")){
            return "";
        } else{
            return this.measure;
        }
    }

    public String getFood() {
        return this.food;
    }

    public static String getCalories(String calories, String servings, int amount_of_people){
        double calo = Double.parseDouble(calories)/ Double.parseDouble(servings);
        double cal = calo * (double) amount_of_people;
        return String.format("%s %,.2f", "Calories: ", cal);
    }
}

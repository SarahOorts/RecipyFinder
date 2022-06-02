package com.example.recipyfinder;

import static com.example.recipyfinder.DetailActivity.EXTRA_SERVINGSIZE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IngredientActivity extends AppCompatActivity {
    //initialiseer variabele
    TextView nutrition;
    TextView energy;
    TextView protein;
    TextView fat;
    TextView carbo;
    private RequestQueue IngredientQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        //initialiseer content van de parent
        super.onCreate(savedInstanceState);
        //initialiseer layout
        setContentView(R.layout.activity_ingredient);

        //zet volley element klaar
        IngredientQueue = Volley.newRequestQueue(this);

        //vraag data op vanuit een intent
        Intent intent = getIntent();
        String ingredient = intent.getStringExtra(EXTRA_SERVINGSIZE);

        //voer functie uit
        getNutrition(ingredient);
    }

    //vraagt data op over nutritionele waarde ingredient
    private void getNutrition(String search){
        //initialiseer layout
        TextView nutrition = findViewById(R.id.text_view_nutrition);
        TextView energy = findViewById(R.id.text_view_energy);
        TextView protein = findViewById(R.id.text_view_protein);
        TextView fat = findViewById(R.id.text_view_fat);
        TextView carbo = findViewById(R.id.text_view_carbohydrates);

        //deel string op om spatie te vervangen door %20 en terug samen te plakken
        String[] surl = search.split(" ", 0);
        StringBuilder list = new StringBuilder();
        for(int o = 0; o < surl.length; o++){
            String in = surl[o] + "%20";
            list.append(in);
        }
        String sterm = list.toString();
        String url = "https://api.edamam.com/api/nutrition-data?app_id=16fda4d7&app_key=471a082f96537f638f441a91c539d51c&nutrition-type=cooking&ingr="+sterm;

        //api call (1 = methode, 2 = url, 3 = request, 4 = success, 5 =  error)
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try{
                            JSONObject jsonObject = response.getJSONObject("totalNutrientsKCal");
                            JSONObject kcal = jsonObject.getJSONObject("ENERC_KCAL");
                            String nutenergy = String.format("Energy: " + kcal.getString("quantity") + " kcal");

                            JSONObject prot = jsonObject.getJSONObject("PROCNT_KCAL");
                            String nutprotein = String.format("Protein: " + prot.getString("quantity") + " kcal");

                            JSONObject fats = jsonObject.getJSONObject("FAT_KCAL");
                            String nutfat = String.format("Fat: " + fats.getString("quantity") + " kcal");

                            JSONObject carbohyd = jsonObject.getJSONObject("CHOCDF_KCAL");
                            String nutcarbo = String.format("Carbohydrates: " + carbohyd.getString("quantity") + " kcal");

                            //voeg opgevraagde data toe aan layout
                            nutrition.setText(search);
                            energy.setText(nutenergy);
                            protein.setText(nutprotein);
                            fat.setText(nutfat);
                            carbo.setText(nutcarbo);
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        IngredientQueue.add(request);
    }

}

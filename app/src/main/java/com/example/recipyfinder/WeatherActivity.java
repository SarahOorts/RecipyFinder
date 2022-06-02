package com.example.recipyfinder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherActivity extends AppCompatActivity{
    //initialiseer public static string om data via intent door te geven
    public static final String EXTRA_CITY = "city";

    //initialiseer volley requestqueue en extra variabele
    TextView temperature;
    private RequestQueue WeatherQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialiseer content van de parent
        super.onCreate(savedInstanceState);
        //initialiseer layout
        setContentView(R.layout.activity_weather);

        //zet volley item klaar
        WeatherQueue = Volley.newRequestQueue(this);

        //link buttons en edittext aan layout
        Button mSearchbtn = findViewById(R.id.search_btn);
        Button inspo = findViewById(R.id.getweather_btn);
        EditText searchfield = findViewById(R.id.input);
        temperature = findViewById(R.id.text_view_temperature);
        Button cuisine = findViewById(R.id.cuisine_btn);
        Button searchAll = findViewById(R.id.searchall_btn);
        Button weather = findViewById(R.id.weather_btn);
        Button mealtype = findViewById(R.id.meal_btn);

        //bij klik vraag nieuwe search op
        mSearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchterm;
                searchterm = searchfield.getText().toString();
                getWeatherbyLocation(searchterm);
            }
        });

        //bij klik vraag nieuwe inspiratie op
        inspo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp = String.valueOf(temperature.getText());
                String[] loct = tmp.split(" ", 0);
                String t_loc = loct[2];

                Intent weatherIntent = new Intent(WeatherActivity.this, MainActivity.class);
                weatherIntent.putExtra(EXTRA_CITY, t_loc);
                startActivity(weatherIntent);
            }
        });

        //bij klik verplaats naar andere activity
        cuisine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cuisineIntent = new Intent(WeatherActivity.this, CuisineActivity.class);
                startActivity(cuisineIntent);
            }
        });

        //bij klik verplaats naar andere activity
        searchAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(WeatherActivity.this, MainActivity.class);
                startActivity(searchIntent);
            }
        });

        //bij klik verplaats naar andere activity
        mealtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mealIntent = new Intent(WeatherActivity.this, MealActivity.class);
                startActivity(mealIntent);
            }
        });

        //bij klik verplaats naar andere activity
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent weatherIntent = new Intent(WeatherActivity.this, WeatherActivity.class);
                startActivity(weatherIntent);
            }
        });
    }

    //functie om api data op te vragen
    private void getWeatherbyLocation(String searchterm){
        String url = "https://api.weatherapi.com/v1/current.json?key=3a06dcc047484792aa6102031211703&q=" + searchterm;

        //api call (1 = methode, 2 = url, 3 = request, 4 = success, 5 =  error)
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONObject jsonObject = response.getJSONObject("current");
                            int temp = jsonObject.getInt("temp_c");
                            temperature.setText(String.format("%s %s", "Temperature: ", temp));
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
        WeatherQueue.add(request);
    }
}

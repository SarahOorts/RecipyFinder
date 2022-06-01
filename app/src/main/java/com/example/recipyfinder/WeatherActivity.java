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
    public static final String EXTRA_CITY = "city";
    TextView temperature;
    private RequestQueue WeatherQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        WeatherQueue = Volley.newRequestQueue(this);

        Button mSearchbtn = findViewById(R.id.search_btn);
        Button weather = findViewById(R.id.getweather_btn);
        EditText searchfield = findViewById(R.id.input);
        temperature = findViewById(R.id.text_view_temperature);

        mSearchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchterm;
                searchterm = searchfield.getText().toString();
                getWeatherbyLocation(searchterm);
            }
        });

        weather.setOnClickListener(new View.OnClickListener() {
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
    }

    private void getWeatherbyLocation(String searchterm){
        String url = "https://api.weatherapi.com/v1/current.json?key=3a06dcc047484792aa6102031211703&q=" + searchterm;

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

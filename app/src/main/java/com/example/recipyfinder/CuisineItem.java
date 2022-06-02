package com.example.recipyfinder;

import android.util.Log;

public class CuisineItem {
    //initialiseer alle variabele die behoren tot een mainitem
    private String cType;

    public CuisineItem(String type){
        cType = type;
    }

    public String getcType() {return cType;}

    //deel string op om spatie te vervangen door %20 en terug samen te plakken
    public String getType(){
        String[] surl = cType.split(" ", 0);
        StringBuilder list = new StringBuilder();
        for(int o = 0; o < surl.length; o++){
            String in = surl[o] + "%20";
            list.append(in);
        }
        String sterm = list.toString();
        return sterm;
    }
}

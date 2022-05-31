package com.example.recipyfinder;

import android.util.Log;

public class CuisineItem {
    private String cType;

    public CuisineItem(String type){
        cType = type;
    }

    public String getcType() {return cType;}

    public String getType(){
        String[] surl = cType.split(" ", 0);
        StringBuilder list = new StringBuilder();
        for(int o = 0; o < surl.length; o++){
            String in = surl[o] + "%20";
            list.append(in);
            Log.d("list", in);
        }
        String sterm = list.toString();
        Log.d("sterm", sterm);
        return sterm;
    }
}

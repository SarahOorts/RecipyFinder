package com.example.recipyfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class CuisineAdapter extends RecyclerView.Adapter<CuisineAdapter.CuisineViewHolder>{
    private Context cContext;
    private ArrayList<CuisineItem> cCuisineList;
    private CuisineAdapter.OnItemClickListener cListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(CuisineAdapter.OnItemClickListener listener){
        cListener = listener; //luistert naar click on main activity
    }

    public CuisineAdapter(Context context, ArrayList<CuisineItem> cuisineList){
        cContext = context;
        cCuisineList = cuisineList;
    }

    @NonNull
    @Override
    public CuisineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(cContext).inflate(R.layout.cuisine_item, parent, false);
        return new CuisineViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CuisineAdapter.CuisineViewHolder holder, int position) {
        CuisineItem currentItem = cCuisineList.get(position);

        String cType = currentItem.getcType();
        holder.cTextViewCuisine.setText(cType);
    }

    @Override
    public int getItemCount() {
        return cCuisineList.size();
    }

    public class CuisineViewHolder extends RecyclerView.ViewHolder{
        public TextView cTextViewCuisine;

        public CuisineViewHolder(@NonNull View itemView) {
            super(itemView);
            cTextViewCuisine = itemView.findViewById(R.id.cuisine_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(cListener != null){
                        int position = getBindingAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            cListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}

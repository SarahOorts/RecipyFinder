package com.example.recipyfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>{
    private Context mContext;
    private ArrayList<MainItem> mMainList;

    public MainAdapter(Context context, ArrayList<MainItem> mainList){
        mContext = context;
        mMainList = mainList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.main_item, parent, false);
        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        MainItem currentItem = mMainList.get(position);

        String imageUrl = currentItem.getmImageUrl();
        String mealName = currentItem.getmMealName();
        int mealId = currentItem.getmMealId();

        holder.mTextViewMealName.setText(mealName);
        holder.mTextViewMealId.setText("id: " + mealId);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mMainList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextViewMealName;
        public TextView mTextViewMealId;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewMealName = itemView.findViewById(R.id.text_view);
            mTextViewMealId = itemView.findViewById(R.id.text_view_id);

        }
    }
}

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
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener; //luistert naar click on main activity
    }

    public MainAdapter(Context context, ArrayList<MainItem> mainList){
        mContext = context;
        mMainList = mainList;
    }

    //maakt nieuwe views aan
    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //voeg layout toe aan views
        View v = LayoutInflater.from(mContext).inflate(R.layout.main_item, parent, false);
        return new MainViewHolder(v);
    }

    //recycleert bestaande views
    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        //voegt data toe aan de views
        MainItem currentItem = mMainList.get(position);

        String imageUrl = currentItem.getmImageUrl();
        String mealName = currentItem.getmMealName();
        String mealCategory = currentItem.getmMealCategory();

        holder.mTextViewMealName.setText(mealName);
        holder.mTextViewMealCategory.setText("Meal category: " + mealCategory);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mMainList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextViewMealName;
        public TextView mTextViewMealCategory;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextViewMealName = itemView.findViewById(R.id.text_view);
            mTextViewMealCategory = itemView.findViewById(R.id.text_view_category);

            //haalt positie op van het geklikte item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getBindingAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}

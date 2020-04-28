package com.humber.capstone.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.humber.capstone.R;
import com.humber.capstone.model.Drawing;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


public class DrawingAdapter extends RecyclerView.Adapter<DrawingAdapter.ViewHolder>{

    private final List<Drawing> mDrawings;
    private final Context mContext;
    private OnItemClickListener mOnItemClickListener;
    Drawing drawing;

    public DrawingAdapter( Context mContext,List<Drawing> mDrawings, OnItemClickListener OnItemClickListener) {
        this.mDrawings = mDrawings;
        this.mContext = mContext;
        this.mOnItemClickListener = OnItemClickListener;
    }

    @NonNull
    @Override
    public DrawingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.image_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView, mOnItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrawingAdapter.ViewHolder holder, int position) {
        drawing = mDrawings.get(position);

        try{
            String imageFile = drawing.image;
            FileInputStream fileInputStream = new FileInputStream(imageFile); //Todo: Change the file source
            Drawable d = Drawable.createFromStream(fileInputStream,null);
            holder.imageView.setImageDrawable(d);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mDrawings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        public View mView;
        OnItemClickListener onItemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            mView = itemView;
            mView.setOnClickListener(this);
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onClick(getAdapterPosition());
            Toast.makeText(mContext,"You selected " + drawing.getImage(), Toast.LENGTH_LONG).show();
        }
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }
}

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
import com.humber.capstone.model.Emoji;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.ViewHolder>{

    private final List<Emoji> mEmojies;
    private final Context mContext;
    private OnItemClickListener mOnItemClickListener;
    Emoji emoji;
    public EmojiAdapter( Context mContext,List<Emoji> mEmojies, OnItemClickListener OnItemClickListener) {
        this.mEmojies = mEmojies;
        this.mContext = mContext;
        this.mOnItemClickListener = OnItemClickListener;
    }

    @NonNull
    @Override
    public EmojiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.image_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView, mOnItemClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmojiAdapter.ViewHolder holder, int position) {
        emoji = mEmojies.get(position);

        try{
            String imageFile = emoji.image;
            InputStream inputStream = mContext.getAssets().open("emojies/" + imageFile);
            Drawable d = Drawable.createFromStream(inputStream,null);
            holder.imageView.setImageDrawable(d);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mEmojies.size();
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
            Toast.makeText(mContext,"You selected " + emoji.getItemName(), Toast.LENGTH_LONG).show();
        }
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }
}

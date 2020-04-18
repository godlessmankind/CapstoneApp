package com.humber.capstone;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.humber.capstone.model.EmojiItem;

import java.util.List;

public class ImageItemAdapter extends ArrayAdapter<EmojiItem> {

    List<EmojiItem> emojieItems;
    LayoutInflater inflater;

    public ImageItemAdapter(@NonNull Context context, @NonNull List<EmojiItem> objects) {
        super(context, R.layout.image_item, objects);
        emojieItems = objects;
        inflater = LayoutInflater.from(context);
    }
}

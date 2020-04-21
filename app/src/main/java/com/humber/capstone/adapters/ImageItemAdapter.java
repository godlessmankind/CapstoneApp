package com.humber.capstone.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.humber.capstone.R;
import com.humber.capstone.model.Emoji;
import com.humber.capstone.model.ImageItem;

import java.util.List;

public class ImageItemAdapter extends ArrayAdapter<ImageItem> {

    List<ImageItem> mImageItems;
    LayoutInflater mInflater;

    public ImageItemAdapter(@NonNull Context context, @NonNull List<ImageItem> objects) {
        super(context, R.layout.image_item, objects);
        mImageItems = objects;
        mInflater = LayoutInflater.from(context);
    }
}

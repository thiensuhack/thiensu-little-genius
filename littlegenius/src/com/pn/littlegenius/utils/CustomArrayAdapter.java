package com.pn.littlegenius.utils;

import java.io.IOException;
import java.io.InputStream;

import com.pn.littlegenius.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/** An array adapter that knows how to render views when given CustomData classes */
public class CustomArrayAdapter extends ArrayAdapter<CustomData> {
    private LayoutInflater mInflater;

    public CustomArrayAdapter(Context context, CustomData[] values) {
        super(context, R.layout.custom_data_view, values);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if ( convertView == null ) 
        {
            // Inflate the view since it does not exist
            convertView = mInflater.inflate(R.layout.custom_data_view, parent, false);

            // Create and save off the holder in the tag so we get quick access to inner fields
            // This must be done for performance reasons
            holder = new Holder();
            holder.imgView = (ImageView) convertView.findViewById(R.id.imgView);
            convertView.setTag(holder);
        } else 
        {
            holder = (Holder) convertView.getTag();
        }
        // Populate the text
        holder.imgView.setBackgroundResource(getItem(position).getBackgroundColor());
        return convertView;
    }

    /** View holder for the views we need access to */
    private static class Holder {
        public ImageView imgView;
    }
}

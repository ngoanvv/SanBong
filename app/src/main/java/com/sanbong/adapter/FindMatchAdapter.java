package com.sanbong.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.sanbong.R;
import com.sanbong.model.Match;

import java.util.ArrayList;

/**
 * Created by Diep_Chelsea on 13/07/2016.
 */
public class FindMatchAdapter extends ArrayAdapter<Match> {

    Context context;
    ArrayList<Match> items;

    public FindMatchAdapter(Context context,int layout, ArrayList<Match> items) {
        super(context,layout);
        context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_match, null);
            holder = new ViewHolder();

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }



        // holder.codeCountry.setText(countryCode.getCntCodeLetter());
        return convertView;


    }

    static class ViewHolder
    {
        String id;
        String time;
        String hostName;
        String stadium;
        String description;
        String location;
        String money;
    }
}

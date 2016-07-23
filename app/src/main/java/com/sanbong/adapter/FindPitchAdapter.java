package com.sanbong.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sanbong.R;
import com.sanbong.model.Pitch;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Diep_Chelsea on 20/07/2016.
 */
public class FindPitchAdapter extends RecyclerView.Adapter<FindPitchAdapter.RecyclerViewHolder> {

    ArrayList<Pitch> list;
    Activity context;
    public FindPitchAdapter(Activity context, ArrayList<Pitch> listData) {
        this.context = context;
        this.list = listData;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemview = inflater.inflate(R.layout.item_pitch, parent, false);
        return new RecyclerViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_owner.setText(list.get(position).getOwnerName());
        holder.tv_contact.setText(list.get(position).getOwnerPhone());
        holder.tv_location.setText(list.get(position).getLocation());
//        holder.tv_stadium.setText(list.get(position).getStadium());
//        holder.tv_money.setText(list.get(position).getMoney());
    }


    @Override
    public int getItemCount() {
        return 0;
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_owner,tv_location,tv_money,tv_contact;
        FancyButton accept,readmore;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.item_name);
            tv_owner = (TextView) itemView.findViewById(R.id.item_owner);
            tv_location = (TextView) itemView.findViewById(R.id.item_location);
            tv_money = (TextView) itemView.findViewById(R.id.item_money);
            tv_contact = (TextView) itemView.findViewById(R.id.item_contact);
            accept = (FancyButton) itemView.findViewById(R.id.bt_accept);
            readmore = (FancyButton) itemView.findViewById(R.id.bt_readmore);

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
            readmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("match click","true");
                }
            });
        }

    }
}

package com.sanbong.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sanbong.R;
import com.sanbong.model.Match;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Diep_Chelsea on 13/07/2016.
 */
public class FindMatchAdapter extends RecyclerView.Adapter<FindMatchAdapter.RecyclerViewHolder>{

    ArrayList<Match> list;
    Activity context;
    public FindMatchAdapter(Activity context,ArrayList<Match> listData) {
        this.context = context;
        this.list = listData;
    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemview = inflater.inflate(R.layout.item_match, parent, false);
            return new RecyclerViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.tv_descrip.setText(list.get(position).getDescription());
        holder.tv_hostname.setText(list.get(position).getHostName());
        holder.tv_time.setText(list.get(position).getTime());
        holder.tv_location.setText(list.get(position).getLocation());
        holder.tv_stadium.setText(list.get(position).getStadium());
        holder.tv_money.setText(list.get(position).getMoney());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_hostname,tv_time,tv_location,tv_stadium,tv_money,tv_descrip;
        LinearLayout ll_match;
        FancyButton accept,readmore;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            tv_hostname = (TextView) itemView.findViewById(R.id.items_name);
            tv_time = (TextView) itemView.findViewById(R.id.item_time);
            tv_location = (TextView) itemView.findViewById(R.id.item_location);
            tv_stadium = (TextView) itemView.findViewById(R.id.item_stadium);
            tv_money = (TextView) itemView.findViewById(R.id.item_money);
            tv_descrip = (TextView) itemView.findViewById(R.id.item_description);
            ll_match = (LinearLayout) itemView.findViewById(R.id.ll_match);
            accept = (FancyButton) itemView.findViewById(R.id.bt_accept);
            readmore = (FancyButton) itemView.findViewById(R.id.bt_readmore);

            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    matchClickInterface.onClickAcceptMatch();

                }
            });
            readmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    matchClickInterface.onClickReadMore();

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


    public MatchClickInterface matchClickInterface;
    public void setMatchClickInterface(MatchClickInterface matchClickInterface)
    {
        this.matchClickInterface = matchClickInterface;
    }
    public interface MatchClickInterface
    {
        public void onClickAcceptMatch();
        public void onClickReadMore();
    }
}

package com.sanbong.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sanbong.R;
import com.sanbong.model.Match;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Diep_Chelsea on 13/07/2016.
 */
public class FindMatchAdapter extends RecyclerView.Adapter<FindMatchAdapter.RecyclerViewHolder> implements Filterable{
    public static String TAG="FindMacthAdapter";
    public ArrayList<Match> list;
    public Activity context;
    public FindMatchAdapter(Activity context,ArrayList<Match> listData) {
        this.context = context;
        this.list = listData;
    }
    ArrayList<Match> results;

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemview = inflater.inflate(R.layout.item_match, parent, false);
            return new RecyclerViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {

        holder.tv_descrip.setText(list.get(position).getDescription());
        holder.tv_hostname.setText(list.get(position).getHostName());
        holder.tv_time.setText(list.get(position).getTime());
        holder.tv_location.setText(list.get(position).getLocation());
        holder.tv_stadium.setText(list.get(position).getStadium());
        holder.tv_money.setText(list.get(position).getMoney());
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchClickInterface.onClickAcceptMatch(list.get(position));

            }
        });
        holder.readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchClickInterface.onClickReadMore(list.get(position));
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matchClickInterface.onClickReadMore(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                list = (ArrayList<Match>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<Match> filteredArray = getFilteredResults(constraint);
                results.count = filteredArray.size();
                results.values = filteredArray;
                return results;
            }
        };

        return filter;
    }

    private ArrayList<Match> getFilteredResults(CharSequence constraint) {
        Log.d(TAG,"constraint "+constraint.toString());
        Log.d(TAG,"list "+list.size());

        int count=0;
        results = new ArrayList<>();
        constraint=constraint.toString().toLowerCase();
        for(int i =0;i<list.size();i++)
        {
            if(list.get(i).getLocation().toLowerCase().contains(constraint) || list.get(i).getStadium().toLowerCase().contains(constraint)
              ||      list.get(i).getHostName().toLowerCase().contains(constraint) ||list.get(i).getDescription().toLowerCase().contains(constraint) ||
            list.get(i).getMoney().toLowerCase().contains(constraint) || list.get(i).getTime().toLowerCase().contains(constraint)
                    )
            {
                results.add(list.get(i));
                count++;
            }
        }
        Log.d(TAG,"count "+count);
        Log.d(TAG,"result size: "+results.size());
        return results;
    }
    public void initList()
    {
        list = new ArrayList<>();
        list.add(new Match("1","18h ngày 11-7","Chelsea FC","FECON  Stadium","Đá giao lưu nhẹ nhàng, mong gặp đối thủ lâu dài thường xuyên, liên hệ số 0998989898","144 Xuân Thủy, Cầu Giấy","100k chia đôi"));
        list.add(new Match("1","18h ngày 12-7","MU FC","Old Traford Stadium","Đá giao lưu","144 Hồ tùng mậu , Cầu Giấy sdadasdasdsadasdsadasdasdasdasd","100k chia đôi"));
        list.add(new Match("1","18h ngày 13-7","MC FC","ETIHAD Stadium","Đá giao lưu","144 Xuân Thủy, Cầu Giấy","100k chia đôi"));
        list.add(new Match("1","18h ngày 14-7","Arsenal FC","Emirates Bridge Stadium","Đá giao lưu","144 Xuân Thủy, Cầu Giấy","100k chia đôi"));
        list.add(new Match("1","18h ngày 11-7","Chelsea FC","Stamford Bridge Stadium","Đá giao lưu","144 Xuân Thủy, Cầu Giấy, Hà Nội","100k chia đôi"));

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


        }

    }


    public MatchClickInterface matchClickInterface;
    public void setMatchClickInterface(MatchClickInterface matchClickInterface)
    {
        this.matchClickInterface = matchClickInterface;
    }
    public interface MatchClickInterface
    {
        public void onClickAcceptMatch(Match match);
        public void onClickReadMore(Match match);
    }
}

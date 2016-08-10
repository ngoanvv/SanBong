package com.sanbong.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.sanbong.R;
import com.sanbong.model.Pitch;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Diep_Chelsea on 20/07/2016.
 */
public class FindPitchAdapter extends RecyclerView.Adapter<FindPitchAdapter.RecyclerViewHolder> implements Filterable{
    private static String TAG="FindPitchAdapter";
    ArrayList<Pitch> list;
    Activity context;
    public FindPitchAdapter(Activity context, ArrayList<Pitch> listData) {
        this.context = context;
        this.list = listData;
    }
    ArrayList<Pitch> results;
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
        holder.tv_money.setText(list.get(position).getMoney());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }
    public ArrayList<Pitch> initData()
    {
        list = new ArrayList<>();
        list.add(new Pitch("id","id","600k 2 tiếng","a bc ","144 Xuân thủy, Cầu Giấy","MR Dương","0989793382"));
        list.add(new Pitch("id","id","600k 2 tiếng","d e f","144 Xuân thủy, Cầu Giấy","MR Dương","0989793382"));
        list.add(new Pitch("id","id","600k 2 tiếng","acx","144 Xuân thủy, Cầu Giấy","MR Dương","0989793382"));
        list.add(new Pitch("id","id","600k 2 tiếng","Sân bóng FECON","144 Xuân thủy, Cầu Giấy","MR Dương","0989793382"));
        list.add(new Pitch("id","id","600k 2 tiếng","Sân bóng FECON","144 Xuân thủy, Cầu Giấy","MR Dương","0989793382"));
        list.add(new Pitch("id","id","600k 2 tiếng","Sân bóng FECON","144 Xuân thủy, Cầu Giấy","MR Dương","0989793382"));
        return list;
    }
    public void initList()
    {
        list=initData();
    }
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                
                list = (ArrayList<Pitch>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();
                ArrayList<Pitch> filteredArray = getFilteredResults(constraint);
                results.count = filteredArray.size();
                results.values = filteredArray;
                return results;
            }
        };

        return filter;
    }

    private ArrayList<Pitch> getFilteredResults(CharSequence constraint) {
        results = new ArrayList<>();
        int count=0;
        constraint = constraint.toString().toLowerCase();
        Log.d(TAG,"constraint "+constraint.length());
        Log.d(TAG,"list "+list.size());
        for(int i =0;i<list.size();i++)
        {
            if(list.get(i).getName().toLowerCase().contains(constraint) || list.get(i).getLocation().toLowerCase().contains(constraint)
                    || list.get(i).getMoney().toLowerCase().contains(constraint)
                    || list.get(i).getOwnerName().toLowerCase().contains(constraint)
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

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_owner,tv_location,tv_money,tv_contact;
        FancyButton booknow,readmore;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.item_name);
            tv_owner = (TextView) itemView.findViewById(R.id.item_owner);
            tv_location = (TextView) itemView.findViewById(R.id.item_location);
            tv_money = (TextView) itemView.findViewById(R.id.item_money);
            tv_contact = (TextView) itemView.findViewById(R.id.item_contact);
            booknow = (FancyButton) itemView.findViewById(R.id.bt_booknow);
            readmore = (FancyButton) itemView.findViewById(R.id.bt_readmore);

            booknow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPitchClickInterface.clickOrderNow();
                }
            });
            readmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPitchClickInterface.clickReadmore();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("pitch click","true");
                }
            });
        }
    }
    public PitchClickInterface mPitchClickInterface;

    public void setmPitchClickInterface(PitchClickInterface mPitchClickInterface) {
        this.mPitchClickInterface = mPitchClickInterface;
    }

    public interface PitchClickInterface
    {
        void clickOrderNow();
        void clickReadmore();
    }
}

package com.natsu.nestsync;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private Context context;
    private ArrayList<String> nestLists;
    private static OnRecyclerItemClickListener mRecListener;

    public HomeAdapter(Context context, ArrayList<String> nestLists, OnRecyclerItemClickListener mRecListener){
        this.context = context;
        this.nestLists = nestLists;
        this.mRecListener = mRecListener;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.listcard_home,parent,false);
        return new HomeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {

        String name = nestLists.get(position);
        holder.listname.setText(name);
    }

    @Override
    public int getItemCount() {
        return nestLists.size();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView listname;
        Button listdel;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            listname = itemView.findViewById(R.id.listname);
            listdel = itemView.findViewById(R.id.listdel);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mRecListener != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            mRecListener.onRecItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}

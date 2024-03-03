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

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> nestLists;

    public HomeAdapter(Context context, ArrayList<String> nestLists){
        this.context = context;
        this.nestLists = nestLists;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.listitem_home,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String name = nestLists.get(position);
        holder.listname.setText(name);
    }

    @Override
    public int getItemCount() {
        return nestLists.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView listname;
        Button listdel;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            listname = itemView.findViewById(R.id.listname);
            listdel = itemView.findViewById(R.id.listdel);
        }
    }
}

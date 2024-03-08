package com.natsu.nestsync;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.natsu.nestsync.models.Item;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    Context context;
    ArrayList<Item> items;

    public ListAdapter(Context context, ArrayList<Item> items){
        this.context = context;
        this.items = items;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemcard_listview,parent,false);
        return new ListAdapter.ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        String item_text = items.get(position).getItemName();
        Boolean status = items.get(position).getItemStatus();
        holder.itemText.setText(item_text);
        holder.itemStatus.setChecked(status);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ListViewHolder extends RecyclerView.ViewHolder {
        EditText itemText;
        CheckBox itemStatus;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            itemText = itemView.findViewById(R.id.itemText);
            itemStatus = itemView.findViewById(R.id.itemCheck);
        }
    }
}

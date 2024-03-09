package com.natsu.nestsync;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.natsu.nestsync.models.Item;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {
    private Context context;
    private ArrayList<Item> items;
    private static ArrayList<String> ids;
    private static OnRecyclerItemClickListener nRecListener;

    public ListAdapter(Context context, ArrayList<Item> items, ArrayList<String> ids, OnRecyclerItemClickListener nRecListener){
        this.context = context;
        this.items = items;
        this.ids = ids;
        this.nRecListener = nRecListener;
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
        holder.itemText.setText(item_text);
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

            itemText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(nRecListener != null && !hasFocus){
                        int pos = getAdapterPosition();
                        String id = ids.get(pos);
                        String newText = itemText.getText().toString();
                        if (pos != RecyclerView.NO_POSITION){
                            nRecListener.onRecItemTextClick(id,newText);
                        }
                    }
                }
            });

            itemStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(nRecListener != null && isChecked){
                        int pos = getAdapterPosition();
                        String id = ids.get(pos);
                        if (pos != RecyclerView.NO_POSITION){
                            nRecListener.onRecItemBtnClick(pos,id);
                        }
                    }
                }
            });
        }
    }
}

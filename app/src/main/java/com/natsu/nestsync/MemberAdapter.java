package com.natsu.nestsync;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.natsu.nestsync.models.Item;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MemberViewHolder> {
    private Context context;
    private static ArrayList memNames;
    private static ArrayList memIds;
    private static OnRecyclerItemClickListener mRecListener;

    public MemberAdapter(Context context, ArrayList names, ArrayList ids, OnRecyclerItemClickListener mRecListener){
        this.context = context;
        this.memNames = names;
        this.memIds = ids;
        this.mRecListener = mRecListener;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.membercard_members,parent,false);
        return new MemberAdapter.MemberViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        String memName = memNames.get(position).toString();
        holder.posMemSwitch.setText(memName);
    }

    @Override
    public int getItemCount() {
        return memNames.size();
    }

    public static class MemberViewHolder extends RecyclerView.ViewHolder {
        Switch posMemSwitch;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);

            posMemSwitch = itemView.findViewById(R.id.posMemSwitch);

            posMemSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (mRecListener != null && isChecked) {
                        int pos = getAdapterPosition();
                        String id = memIds.get(pos).toString();
                        if (pos != RecyclerView.NO_POSITION) {
                            mRecListener.onRecItemClick(pos,id);
                        }
                    } else if (mRecListener != null && !isChecked){
                        int pos = getAdapterPosition();
                        String id = memIds.get(pos).toString();
                        if (pos != RecyclerView.NO_POSITION) {
                            mRecListener.onRecItemBtnClick(pos,id);
                        }
                    }
                }
            });
        }
    }
}

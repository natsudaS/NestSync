package com.natsu.nestsync;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.recyclerview.widget.RecyclerView;

import com.natsu.nestsync.models.Item;

import java.util.ArrayList;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {
    private Context context;
    private ArrayList<String> names;
    private static ArrayList<String> uids;
    private static OnRecyclerItemClickListener fRecListener;
    public FriendsAdapter(Context context, ArrayList<String> names, ArrayList<String> uids, OnRecyclerItemClickListener fRecListener){
        this.context = context;
        this.names = names;
        this.uids = uids;
        this.fRecListener = fRecListener;
    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.friendcard_friends,parent,false);
        return new FriendsAdapter.FriendsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {
        String friendName = names.get(position);
        holder.friendName.setText(friendName);
    }

    @Override
    public int getItemCount() {return names.size();}

    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        TextView friendName;
        Button delButton;

        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);

            friendName = itemView.findViewById(R.id.friendName);
            delButton = itemView.findViewById(R.id.delFriend);

            delButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(fRecListener != null){
                        int pos = getAdapterPosition();
                        String id = uids.get(pos);
                        if (pos != RecyclerView.NO_POSITION){
                            fRecListener.onRecItemBtnClick(pos,id);
                        }
                    }
                }
            });
        }
    }
}

package com.boxfox.chefriapplication.postcard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boxfox.chefriapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boxfox on 2017-06-03.
 */

public class PostCardAdaptor extends RecyclerView.Adapter<PostCardViewHolder> {
    private List<PostCardItem> items;

    public PostCardAdaptor(){
        items = new ArrayList<PostCardItem>();
    }

    @Override
    public PostCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        PostCardViewHolder holder = new PostCardViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(PostCardViewHolder holder, int position) {
        PostCardItem item = items.get(position);
        holder.setName(item.getName());
        holder.setText(item.getSubInfo());
        holder.setProfileImage(item.getProfileImageUrl());
        holder.setBackgroundImage(item.getBgimageUrl());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void add(PostCardItem item){
        items.add(item);
        this.notifyDataSetChanged();
    }
}

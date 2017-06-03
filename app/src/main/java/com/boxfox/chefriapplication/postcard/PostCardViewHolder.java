package com.boxfox.chefriapplication.postcard;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.boxfox.chefriapplication.R;
import com.pkmmte.view.CircularImageView;

/**
 * Created by boxfox on 2017-06-03.
 */

public class PostCardViewHolder extends RecyclerView.ViewHolder {
    private View bgView;
    private CircularImageView profileImageView;

    private TextView tv_name, tv_info;

    public PostCardViewHolder(View itemView) {
        super(itemView);
        bgView = itemView.findViewById(R.id.view);
        profileImageView = (CircularImageView) itemView.findViewById(R.id.avatar);
        tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        tv_info = (TextView) itemView.findViewById(R.id.tv_info);
    }

    public void setName(String name){
        tv_name.setText(name);
    }

    public void setText(String str){
        tv_info.setText(str);
    }

    public void setProfileImage(String url){

    }

    private void setRandomColor(){
        bgView.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.accent_light));
        profileImageView.setBorderColor(itemView.getContext().getResources().getColor(R.color.accent_light));
    }

}

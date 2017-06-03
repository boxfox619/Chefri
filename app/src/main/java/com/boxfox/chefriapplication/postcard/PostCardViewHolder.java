package com.boxfox.chefriapplication.postcard;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.boxfox.chefriapplication.R;
import com.pkmmte.view.CircularImageView;

/**
 * Created by boxfox on 2017-06-03.
 */

public class PostCardViewHolder extends RecyclerView.ViewHolder {
    private View bgView;
    private CircularImageView profileImageView;
    private ImageView bgImageView;

    private TextView tv_name, tv_info;

    public PostCardViewHolder(View itemView) {
        super(itemView);
        bgView = itemView.findViewById(R.id.view);
        profileImageView = (CircularImageView) itemView.findViewById(R.id.avatar);
        tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        tv_info = (TextView) itemView.findViewById(R.id.tv_info);
        bgImageView = (ImageView) itemView.findViewById(R.id.bg_image);
    }

    public void setName(String name){
        tv_name.setText(name);
    }

    public void setText(String str){
        tv_info.setText(str);
    }

    public void setProfileImage(String url){
        AQuery aq = new AQuery(itemView.getContext());
        aq.id(profileImageView).image(url, true, true, 0, R.drawable.user, null, AQuery.FADE_IN);
    }

    public void setBackgroundImage(String url){
        AQuery aq = new AQuery(itemView.getContext());
        aq.id(bgImageView).image(url, true, true, 0, R.drawable.user, null, AQuery.FADE_IN);
    }

    private void setRandomColor(){
        bgView.setBackgroundColor(itemView.getContext().getResources().getColor(R.color.accent_light));
        profileImageView.setBorderColor(itemView.getContext().getResources().getColor(R.color.accent_light));
    }

}

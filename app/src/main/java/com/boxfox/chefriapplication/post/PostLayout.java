package com.boxfox.chefriapplication.post;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.androidquery.AQuery;
import com.boxfox.chefriapplication.R;
import com.boxfox.chefriapplication.RecipeWriteActivity;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by boxfox on 2017-06-13.
 */

public class PostLayout extends LinearLayout {
    private JSONObject document;

    public PostLayout(Context context) {
        this(context, null);
    }

    public PostLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        document = new JSONObject();
    }

    public void addSmallText() {
        EditText et = (EditText) ((Activity) getContext()).getLayoutInflater().inflate(R.layout.view_post_et_content, null);
        super.addView(et);
        et.addTextChangedListener(new PostWriteTextWatcher(et));
    }

    public void addLageText() {
        EditText et = (EditText) ((Activity) getContext()).getLayoutInflater().inflate(R.layout.view_post_et_title, null);
        super.addView(et);
        et.addTextChangedListener(new PostWriteTextWatcher(et));
    }

    public void addImage(String url) {
        ImageView imageview = (ImageView) ((Activity) getContext()).getLayoutInflater().inflate(R.layout.view_post_image, null);
        AQuery aq = new AQuery(getContext());
        aq.id(imageview).image(url);
        super.addView(imageview);
    }

    public void addImage(Drawable drawable) {
        ImageView imageview = (ImageView) ((Activity) getContext()).getLayoutInflater().inflate(R.layout.view_post_image, null);
        imageview.setImageDrawable(drawable);
        super.addView(imageview);
    }

    public void drawAsPost(JSONObject document) {
        this.document = document;
        drawAsPost();
    }

    public void drawAsPost() {

    }

    public void drawAsEditor(JSONObject document) {
        this.document = document;
        drawAsEditor();
    }

    public void drawAsEditor() {

    }

    public JSONObject getDocument() {
        return document;
    }

    private class PostWriteTextWatcher implements TextWatcher {
        private View view;

        PostWriteTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (count == 0 && s.length() == 0) {
                PostLayout.super.removeView(view);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    }

}

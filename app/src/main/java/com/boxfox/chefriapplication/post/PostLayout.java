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
import android.widget.TextView;

import com.androidquery.AQuery;
import com.boxfox.chefriapplication.R;
import com.boxfox.chefriapplication.RecipeWriteActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by boxfox on 2017-06-13.
 */

public class PostLayout extends LinearLayout {
    private boolean mode = false;
    private JSONObject document;

    public PostLayout(Context context) {
        this(context, null);
    }

    public PostLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        document = new JSONObject();
    }

    public View addSmallText() {
        View view;
        if (mode) {
            TextView tv = (EditText) ((Activity) getContext()).getLayoutInflater().inflate(R.layout.view_post_tv_content, null);
            view = tv;
        } else {
            EditText et = (EditText) ((Activity) getContext()).getLayoutInflater().inflate(R.layout.view_post_et_content, null);
            String key = document.length() + "";
            try {
                document.put(key, new JSONObject().put("Type", "Title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            et.addTextChangedListener(new PostWriteTextWatcher(et, key));
            view = et;
        }
        super.addView(view);
        return view;
    }

    public View addLageText() {
        View view;
        if (mode) {
            TextView tv = (EditText) ((Activity) getContext()).getLayoutInflater().inflate(R.layout.view_post_tv_title, null);
            view = tv;
        } else {
            EditText et = (EditText) ((Activity) getContext()).getLayoutInflater().inflate(R.layout.view_post_et_title, null);
            String key = document.length() + "";
            try {
                document.put(key, new JSONObject().put("Type", "Title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            et.addTextChangedListener(new PostWriteTextWatcher(et, key));
            view = et;
        }
        super.addView(view);
        return view;
    }

    public void addImage(String url) {
        ImageView imageview = (ImageView) ((Activity) getContext()).getLayoutInflater().inflate(R.layout.view_post_image, null);
        AQuery aq = new AQuery(getContext());
        aq.id(imageview).image(url);
        if (!mode) {
            String key = document.length() + "";
            try {
                document.put(key, new JSONObject().put("Type", "Image").put("Url", url));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        super.addView(imageview);
    }

    public void addImage(Drawable drawable) {
        ImageView imageview = (ImageView) ((Activity) getContext()).getLayoutInflater().inflate(R.layout.view_post_image, null);
        imageview.setImageDrawable(drawable);
        super.addView(imageview);
    }

    public void drawAsPost(JSONObject document) throws JSONException {
        this.document = document;
        drawAsPost();
    }

    public void drawAsPost() throws JSONException {
        mode = true;
        draw();
    }

    public void drawAsEditor(JSONObject document) throws JSONException {
        this.document = document;
        drawAsEditor();
    }

    public void drawAsEditor() throws JSONException {
        mode = false;
        draw();
    }

    public void draw() throws JSONException {
        removeAllViews();
        Iterator<String> keys = document.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            JSONObject object = document.getJSONObject(key);
            if (object.getString("Type").equals("Title")) {
                ((EditText) addLageText()).setText(object.getString("Content"));
            } else if (object.getString("Type").equals("Content")) {
                ((EditText) addSmallText()).setText(object.getString("Content"));
            } else if (object.getString("Type").equals("Image")) {
                addImage(object.getString("Url"));
            }
        }
    }

    public JSONObject getDocument() {
        return document;
    }

    private class PostWriteTextWatcher implements TextWatcher {
        private View view;
        private String key;

        PostWriteTextWatcher(View view, String key) {
            this.view = view;
            this.key = key;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (count == 0 && s.length() == 0) {
                PostLayout.super.removeView(view);
                document.remove(key);
            } else {
                try {
                    document.getJSONObject(key).put("Content", ((EditText) view).getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

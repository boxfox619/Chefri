package com.boxfox.chefriapplication.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by boxfox on 2017-06-15.
 */

public abstract class ViewController {
    private View view;
    public ViewController(View view){
        this.view = view;
    }

    public abstract void init();

    public View getView(){
        return view;
    }

    public Context getContext(){
        return view.getContext();
    }

    public String getString(int id){
        return getContext().getString(id);
    }

    public View findViewById(int id){
        return view.findViewById(id);
    }

    public Button findButtonById(int id){
        return (Button)findViewById(id);
    }

    public ImageView findImageViewById(int id){
        return (ImageView)findViewById(id);
    }

    public EditText findEditTextById(int id){
        return (EditText)findViewById(id);
    }
}

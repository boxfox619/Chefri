package com.boxfox.chefriapplication.view;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.boxfox.chefriapplication.R;
import com.boxfox.chefriapplication.SearchActivity;

/**
 * Created by boxfox on 2017-06-15.
 */

public class SearchBar extends ViewController implements View.OnClickListener{

    public SearchBar(View view) {
        super(view);
    }

    @Override
    public void init() {
        findViewById(R.id.search_by_food).setOnClickListener(this);
        findViewById(R.id.search_by_tools).setOnClickListener(this);
        findViewById(R.id.search_by_kcal).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String target = getString(R.string.search_food);
        switch(v.getId()){
            case R.id.search_by_tools:
                target = getString(R.string.search_tools);
                break;
            case R.id.search_by_kcal:
                target = getString(R.string.search_kcal);
        }
        Intent intent = new Intent(getContext(), SearchActivity.class);
        getContext().startActivity(intent);
    }
}

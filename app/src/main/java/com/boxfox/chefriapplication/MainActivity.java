package com.boxfox.chefriapplication;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.boxfox.chefriapplication.databinding.ActivityMainBinding;
import com.boxfox.chefriapplication.postcard.PostCardAdaptor;
import com.boxfox.chefriapplication.postcard.PostCardItem;
import com.boxfox.chefriapplication.view.SearchBar;

public class MainActivity extends AppCompatActivity {

    private boolean isSearchShow;

    private PostCardAdaptor cardAdaptor;
    private View searchLayout;

    //private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        isSearchShow = true;
        new SearchBar(findViewById(R.id.searchLayout)).init();
        final RecyclerView cardListView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        cardListView.setLayoutManager(layoutManager);
        cardListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (isSearchShow) {
                        searchLayout.animate().translationY(-searchLayout.getHeight());
                        isSearchShow = false;
                    }
                } else {
                    if (!isSearchShow) {
                        searchLayout.animate().translationY(0);
                        isSearchShow = true;
                    }
                }
            }
        });
        cardAdaptor = new PostCardAdaptor();
        cardListView.setAdapter(cardAdaptor);
        cardAdaptor.add(new PostCardItem(1, "test", "subInfo asdasd", 50, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRJTkGxzhDu3iCYyt2Qg8Yi8LKy_vBK-uQwac2SRjwxCgA30Rujsw", "https://logopond.com/logos/685456e8a158ec3138b1eae0b6a321cc.png"));
        cardAdaptor.add(new PostCardItem(2, "test", "subInfo asdasd", 50, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRJTkGxzhDu3iCYyt2Qg8Yi8LKy_vBK-uQwac2SRjwxCgA30Rujsw", "https://logopond.com/logos/685456e8a158ec3138b1eae0b6a321cc.png"));
        cardAdaptor.add(new PostCardItem(3, "test", "subInfo asdasd", 50, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRJTkGxzhDu3iCYyt2Qg8Yi8LKy_vBK-uQwac2SRjwxCgA30Rujsw", "https://logopond.com/logos/685456e8a158ec3138b1eae0b6a321cc.png"));
    }
}

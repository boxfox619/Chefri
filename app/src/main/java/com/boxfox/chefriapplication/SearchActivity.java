package com.boxfox.chefriapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.boxfox.chefriapplication.postcard.PostCardAdaptor;
import com.boxfox.chefriapplication.view.DetailSearchBar;

public class SearchActivity extends AppCompatActivity {
    private PostCardAdaptor cardAdaptor;
    private DetailSearchBar detailSearchBar;
    private boolean isSearchShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Log.e("test", "avasvasv");

        isSearchShow = true;
        cardAdaptor = new PostCardAdaptor();


        detailSearchBar = new DetailSearchBar(findViewById(R.id.search_bar));
        detailSearchBar.init();
        detailSearchBar.setAdpater(cardAdaptor);

        final RecyclerView cardListView = (RecyclerView) findViewById(R.id.recycler_view);
        cardListView.setAdapter(cardAdaptor);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        cardListView.setLayoutManager(layoutManager);
        cardListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (isSearchShow) {
                        detailSearchBar.getView().animate().translationY(detailSearchBar.getView().getHeight());
                        isSearchShow = false;
                    }
                } else {
                    if (!isSearchShow) {
                        detailSearchBar.getView().animate().translationY(0);
                        isSearchShow = true;
                    }
                }
            }
        });
    }
}

package com.boxfox.chefriapplication.view;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.boxfox.chefriapplication.R;
import com.boxfox.chefriapplication.postcard.PostCardAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.OnClick;

/**
 * Created by boxfox on 2017-06-15.
 */

public class DetailSearchBar extends ViewController {
    private AQuery aq;
    private ProgressDialog progressDialog;
    private EditText et_searchContext;

    private PostCardAdaptor adpater;

    public DetailSearchBar(View view) {
        super(view);
    }

    @Override
    public void init() {
        et_searchContext = findEditTextById(R.id.ed_home_searchbar);
        findImageViewById(R.id.iv_search_icon).setOnClickListener(new SearchClickListener());
        findViewById(R.id.iv_more_icon).setOnClickListener(new MoreClickListener());
        progressDialog = new ProgressDialog(getContext());
    }

    public void setAdpater(PostCardAdaptor adpater){
        this.adpater = adpater;
    }

    public void chagneTarget(String str){
        et_searchContext.setText(str);
    }

    private class SearchClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(aq!=null) {
                return;
            }
            progressDialog.show();
            aq = new AQuery(getContext());
            aq.ajax(getString(R.string.search_url), String.class, new AjaxCallback<String>(){
                @Override
                public void callback(String url, String object,
                                     AjaxStatus status) {
                    if(status.getCode() == 200){
                        try {
                            JSONArray arr = new JSONArray(object);
                            /** @TODO recyclerview */
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }else{
                        Toast.makeText(getContext(), "검색에 실패했습니다!", Toast.LENGTH_SHORT).show();
                    }
                    aq = null;
                    progressDialog.hide();
                }
            });
            et_searchContext.getText();
        }
    }

    private class MoreClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

        }
    }

}

package com.boxfox.chefriapplication;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.json.JSONException;
import org.json.JSONObject;

public class RecipeDetailActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        progressDialog = new ProgressDialog(RecipeDetailActivity.this);

        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        load(getIntent().getIntExtra("number", 0));
    }

    private void load(int num){
        progressDialog.show();
        AQuery aq = new AQuery(RecipeDetailActivity.this);
        aq.ajax("", String.class, new AjaxCallback<String>(){
            public void callback(String url, String result, AjaxStatus status) {
                if (result != null) {
                    try {
                        JSONObject object = new JSONObject(result);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(RecipeDetailActivity.this, "레시피를 읽어오지 못했습니다.", Toast.LENGTH_LONG).show();
                }
                progressDialog.hide();
            };
        });
    }

}

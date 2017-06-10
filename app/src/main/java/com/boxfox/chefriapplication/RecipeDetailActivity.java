package com.boxfox.chefriapplication;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RecipeDetailActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    @InjectView(R.id.recipe_name) TextView tv_recipeName;
    @InjectView(R.id.recipe_materials) TextView tv_recipeMaterials;
    @InjectView(R.id.recipe_lovers) TextView tv_recipeLovers;
    @InjectView(R.id.cook_duration) TextView tv_cookDuration;
    @InjectView(R.id.recipe_kcal) TextView tv_recipeKcal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.inject(this);
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
                        tv_recipeName.setText(object.getString("Name"));
                        JSONArray materials = object.getJSONArray("Materials");
                        tv_recipeMaterials.setText(materials.getString(0));
                        for(int i = 1; i < materials.length() ;i++){
                            tv_recipeMaterials.append(", "+materials.getString(i));
                        }
                        tv_recipeKcal.setText(object.getString("Kcal"));
                        tv_recipeLovers.setText(object.getString("Lovers"));
                        tv_cookDuration.setText(object.getString("Duration"));
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

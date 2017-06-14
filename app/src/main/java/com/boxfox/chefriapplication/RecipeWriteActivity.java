package com.boxfox.chefriapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.boxfox.chefriapplication.databinding.ActivityRecipeWriteBinding;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class RecipeWriteActivity extends AppCompatActivity {

    public static final String IMG_URL = "";

    private static final int PICK_PHOTO = 123;
    private static final int REQUEST_PERMISSION = 1234;

    private ActivityRecipeWriteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_write);
        binding.btnAddTitle.setOnClickListener(createBtnTitleClickListener());
        binding.btnAddText.setOnClickListener(createBtnTextClickListener());
        binding.btnAddImage.setOnClickListener(createBtnImageClickListener());
        binding.fabSave.setOnClickListener(createFabSaveClickListener());
        binding.tvPostTitle.clearFocus();
        RecipeWriteActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            } else {
                Toast.makeText(RecipeWriteActivity.this, "퍼미션을 허용하지 않았습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            final AQuery aq = new AQuery(RecipeWriteActivity.this);
            final String imageUrl = System.currentTimeMillis() + "";
            File file = null;
            HashMap<String, Object> params = new HashMap<String, Object>();
            params.put(imageUrl, new File(data.getData().getPath()));
            aq.ajax("url", params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object,
                                     AjaxStatus status) {
                    if (status.getCode() == 200) {
                        binding.content.addImage(IMG_URL + imageUrl);
                    } else {
                        binding.content.addImage(getResources().getDrawable(R.drawable.ic_crash_image));
                    }
                }
            });
        }
    }

    private void pickImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
                return;
            }
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO);
    }

    private View.OnClickListener createBtnTitleClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.content.addLageText();
            }
        };
    }

    private View.OnClickListener createBtnTextClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.content.addSmallText();
            }
        };
    }

    private View.OnClickListener createBtnImageClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        };
    }

    private View.OnClickListener createFabSaveClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject document = binding.content.getDocument();
                AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>() {
                    @Override
                    public void callback(String url, JSONObject html, AjaxStatus status) {
                        System.out.println(html);
                    }
                };

                AQuery aq = new AQuery(RecipeWriteActivity.this);
                cb.header("Authorization", "key=yourkey");
                cb.header("Content-Type", "application/json; charset=utf-8");

                Map<String, Object> params = new HashMap<String, Object>();
                params.put(AQuery.POST_ENTITY, document.toString());
                cb.params(params);
                aq.ajax("https://yourdomain.com", JSONObject.class, cb);

            }
        };
    }
}

package com.boxfox.chefriapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.util.Constants;
import com.boxfox.chefriapplication.databinding.ActivityRecipeWriteBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipeWriteActivity extends AppCompatActivity {

    private static final int PICK_PHOTO = 123;
    private static final int REQUEST_PERMISSION = 1234;

    private ActivityRecipeWriteBinding binding;

    private List<String> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_write);
        binding.btnAddTitle.setOnClickListener(createBtnTitleClickListener());
        binding.btnAddText.setOnClickListener(createBtnTextClickListener());
        binding.btnAddImage.setOnClickListener(createBtnImageClickListener());
        binding.fabSave.setOnClickListener(createFabSaveClickListener());
        binding.tvPostTitle.clearFocus();
        imageList = new ArrayList<String>();
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
            params.put(imageUrl, new File(data.getData().getPath());
            aq.ajax("url", params, JSONObject.class, new AjaxCallback<JSONObject>() {
                @Override
                public void callback(String url, JSONObject object,
                                     AjaxStatus status) {
                    ImageView imageview = (ImageView) getLayoutInflater().inflate(R.layout.view_post_image, null);
                    if (status.getCode() == 200) {
                        AQuery aq = new AQuery(RecipeWriteActivity.this);
                        aq.id(imageview).image("url" + imageUrl);
                        imageList.add(imageUrl);
                    } else {
                        imageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_crash_image));
                    }
                    binding.content.addView(imageview);
                }
            });
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);

        parcelFileDescriptor.close();
        return bitmap;
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
                final EditText et = (EditText) getLayoutInflater().inflate(R.layout.view_post_et_title, null);
                binding.content.addView(et);
                et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (count == 0 && s.length() == 0) {
                            binding.content.removeView(et);
                        }
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
        };
    }

    private View.OnClickListener createBtnTextClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText et = (EditText) getLayoutInflater().inflate(R.layout.view_post_et_content, null);
                binding.content.addView(et);
                et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (count == 0 && s.length() == 0) {
                            binding.content.removeView(et);
                        }
                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
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
                JSONArray arr = new JSONArray();
                for (int i = 0; i < binding.content.getChildCount(); i++) {
                    View view = binding.content.getChildAt(i);
                    String type = null;
                    String value = null;
                    if (view instanceof EditText) {
                        if (((EditText) view).getTextSize() == TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics())) {
                            type = "content";
                        } else {
                            type = "title";
                        }
                        value = ((EditText) view).getText().toString();
                    } else if (view instanceof ImageView) {
                        type = "image";
                        value = imageList.get(i);
                    }
                    if (type == null || value == null) continue;
                    try {
                        JSONObject object = new JSONObject();
                        object.put("Type", type);
                        object.put("Value", value);
                        arr.put(object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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
                params.put(AQuery.POST_ENTITY, arr.toString());
                cb.params(params);
                aq.ajax("https://yourdomain.com", JSONObject.class, cb);

            }
        };
    }
}

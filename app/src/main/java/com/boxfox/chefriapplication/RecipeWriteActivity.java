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
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.boxfox.chefriapplication.databinding.ActivityRecipeWriteBinding;

import java.io.FileDescriptor;
import java.io.IOException;

public class RecipeWriteActivity extends AppCompatActivity {

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
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        Log.d("Test", "asd");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (binding.tvPostTitle.isFocused()) {
                Rect outRect = new Rect();
                binding.tvPostTitle.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    binding.tvPostTitle.clearFocus();
                    RecipeWriteActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                }
            }
        }
        return super.dispatchTouchEvent(event);
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
            ImageView imageview = (ImageView) getLayoutInflater().inflate(R.layout.view_post_image, null);
            try {
                imageview.setImageBitmap(getBitmapFromUri(data.getData()));
            } catch (IOException e) {
                e.printStackTrace();
                imageview.setImageDrawable(getResources().getDrawable(R.drawable.ic_crash_image));
            }
            binding.content.addView(imageview);
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
                for (int i = 0; i < binding.content.getChildCount(); i++) {

                }
            }
        };
    }
}

package com.example.instamaterial.ui.activity;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

   /* @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Nullable
    @BindView(R.id.ivLogo)
    ImageView ivLogo;*/

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        bindViews();
    }

    protected void bindViews() {
        //ButterKnife.bind(this);
        setupToolbar();
    }

    protected void setupToolbar() {
       /* if (toolbar != null) {
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowTitleEnabled(false);
            }
        }*/
    }

    /*@Nullable
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Nullable
    public ImageView getIvLogo() {
        return ivLogo;
    }*/

    protected void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}

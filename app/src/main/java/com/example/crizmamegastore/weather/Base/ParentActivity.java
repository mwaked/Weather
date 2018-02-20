package com.example.crizmamegastore.weather.Base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.example.crizmamegastore.weather.R;
import com.example.crizmamegastore.weather.UI.views.Toaster;
import com.example.crizmamegastore.weather.Preferences.SharedPrefManager;
import com.example.crizmamegastore.weather.Utils.DialogUtil;

import butterknife.ButterKnife;


public abstract class ParentActivity extends AppCompatActivity {

    protected AppCompatActivity mActivity;

    protected SharedPrefManager mSharedPrefManager;
    protected Context mContext;
    protected Toaster mToaster;
    protected Bundle mSavedInstanceState;
    Toolbar toolbar;
    TextView tv_toolbar_title;
    private int menuId;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        mContext = this;
        mActivity = this;
        mSharedPrefManager = new SharedPrefManager(mContext);
        mToaster = new Toaster(mContext);

        if (isFullScreen()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (hideInputType()) {
            hideInputTyping();
        }

        // set layout resources
        setContentView(getLayoutResource());
        this.mSavedInstanceState = savedInstanceState;

        ButterKnife.bind(this);
        if (isEnableToolbar()) {
            configureToolbar();
        }
        initializeComponents();

        ButterKnife.bind(this);
        if (isEnableToolbar()) {
            configureToolbar();
        }


    }


    public void setToolbarTitle(String titleId) {
        if (toolbar != null) {
            tv_toolbar_title.setText(titleId);
        }
    }

    protected abstract void initializeComponents();

    private void configureToolbar() {
        toolbar =  findViewById(R.id.toolbar);
        tv_toolbar_title =  findViewById(R.id.tv_toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(ContextCompat.getColor(mContext, R.color.colorWhite));
        // check if enable back
        if (isEnableBack()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toolbar.setNavigationIcon(R.drawable.ic_back);
        }
    }

    protected abstract int getLayoutResource();

    protected abstract boolean isEnableToolbar();

    protected abstract boolean isFullScreen();

    protected abstract boolean isEnableBack();

    protected abstract boolean hideInputType();

    public void createOptionsMenu(int menuId) {
        Log.e("test", "test");
        this.menuId = menuId;
        invalidateOptionsMenu();
    }

    public void removeOptionsMenu() {
        menuId = 0;
        invalidateOptionsMenu();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (menuId != 0) {
            getMenuInflater().inflate(menuId, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_left);
    }

    public void hideInputTyping() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    protected void showProgressDialog(String message) {
        mProgressDialog = DialogUtil.showProgressDialog(this, message, false);
    }

    protected void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void changeStatusBarColor(int Color) {
        Window window = mActivity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(mContext, Color));
    }
}



